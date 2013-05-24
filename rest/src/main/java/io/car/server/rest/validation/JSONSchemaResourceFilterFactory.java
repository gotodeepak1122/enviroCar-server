/*
 * Copyright (C) 2013  Christian Autermann, Jan Alexander Wirwahn,
 *                     Arne De Wall, Dustin Demuth, Saqib Rasheed
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.car.server.rest.validation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Closeables;
import com.google.inject.Inject;
import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ContainerResponseWriter;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;

import io.car.server.core.exception.ValidationException;
import io.car.server.rest.MediaTypes;
import io.car.server.rest.coding.JSONConstants;

/**
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public class JSONSchemaResourceFilterFactory implements ResourceFilterFactory {
    private static final Logger log = LoggerFactory
            .getLogger(JSONSchemaResourceFilterFactory.class);
    private static final boolean VALIDATE_REQUESTS = true;
    private static final boolean VALIDATE_RESPONSES = false;
    private final JsonSchemaFactory schemaFactory;
    private final ObjectReader reader;
    private final ObjectWriter writer;
    private final JsonNodeFactory factory;

    @Inject
    public JSONSchemaResourceFilterFactory(JsonSchemaFactory schemaFactory,
                                           ObjectReader reader,
                                           ObjectWriter writer,
                                           JsonNodeFactory factory) {
        this.schemaFactory = schemaFactory;
        this.reader = reader;
        this.writer = writer;
        this.factory = factory;
    }

    @Override
    public List<ResourceFilter> create(AbstractMethod am) {
        String requestSchema = null;
        String responseSchema = null;
        Schema schema = am.getAnnotation(Schema.class);
        if (schema != null) {
            if (!schema.request().isEmpty()) {
                requestSchema = schema.request();
            }
            if (!schema.response().isEmpty()) {
                responseSchema = schema.response();
            }
        }
        if (requestSchema != null || responseSchema != null) {
            JSONSchemaResourceFilter filter =
                    new JSONSchemaResourceFilter(requestSchema, responseSchema);
            return Collections.<ResourceFilter>singletonList(filter);
        } else {
            return Collections.emptyList();
        }
    }

    protected String getRequestType(AbstractMethod am) throws
            IllegalArgumentException {
        Schema schema = am.getAnnotation(Schema.class);
        if (schema != null && !schema.request().isEmpty()) {
            return schema.request();
        }
        return null;
    }

    protected void validate(String entity, String schema) throws
            ValidationException, IOException {
        try {
            validate(reader.readTree(entity), schemaFactory
                    .getJsonSchema(schema));
        } catch (ProcessingException ex) {
            throw new ValidationException(ex);
        }
    }

    protected void validate(JsonNode t, JsonSchema schema) throws
            ValidationException, ProcessingException {
        ProcessingReport report = schema.validate(t);
        if (!report.isSuccess()) {
            ObjectNode error = factory.objectNode();
            ArrayNode errors = error.putArray(JSONConstants.ERRORS);
            for (ProcessingMessage message : report) {
                errors.add(message.asJson());
            }
            throw new JSONValidationException(error);
        }
    }

    private class JSONSchemaResourceFilter implements ResourceFilter {
        private String request;
        private String response;

        JSONSchemaResourceFilter(String request, String response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public ContainerRequestFilter getRequestFilter() {
            return request == null || !VALIDATE_REQUESTS ? null
                   : new JSONSchemaRequestFilter(request);
        }

        @Override
        public ContainerResponseFilter getResponseFilter() {
            return response == null || !VALIDATE_RESPONSES ? null
                   : new JSONSchemaResponeFilter(response);
        }
    }

    private class JSONSchemaRequestFilter implements ContainerRequestFilter {
        private String schema;

        JSONSchemaRequestFilter(String schema) {
            this.schema = schema;
        }

        @Override
        public ContainerRequest filter(ContainerRequest request) {
            if (request.getMediaType()
                    .isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
                adjustContentType(request);
                validate(request);
            }
            return request;
        }

        protected void adjustContentType(ContainerRequest request) {
            MediaType newMt = new MediaType("application", "json", ImmutableMap
                    .<String, String>builder()
                    .putAll(request.getMediaType().getParameters())
                    .put(MediaTypes.SCHEMA_ATTRIBUTE, schema).build());
            // container request caches the header......
            request.getRequestHeaders().remove(HttpHeaders.CONTENT_TYPE);
            request.getMediaType();
            request.getRequestHeaders()
                    .putSingle(HttpHeaders.CONTENT_TYPE, newMt.toString());
        }

        private void validate(ContainerRequest request) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = request.getEntityInputStream();
            try {
                if (in.available() > 0) {
                    ReaderWriter.writeTo(in, out);
                    byte[] requestEntity = out.toByteArray();
                    ByteArrayInputStream bais =
                            new ByteArrayInputStream(requestEntity);
                    request.setEntityInputStream(bais);
                    String enitity = new String(requestEntity, ReaderWriter
                            .getCharset(request.getMediaType()));
                    JSONSchemaResourceFilterFactory.this
                            .validate(enitity, schema);
                }
            } catch (IOException ex) {
                throw new ContainerException(ex);
            }
        }
    }

    private class JSONSchemaResponeFilter implements ContainerResponseFilter {
        private String schema;

        JSONSchemaResponeFilter(String schema) {
            this.schema = schema;
        }

        @Override
        public ContainerResponse filter(ContainerRequest request,
                                        ContainerResponse response) {
            MediaType mt = response.getMediaType();
            if (mt.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
                adjustContentType(response);
                ContainerResponseWriter crw = response
                        .getContainerResponseWriter();
                ContainerResponseWriter vcrw =
                        new ValidatingWriter(crw, schema);
                response.setContainerResponseWriter(vcrw);
            }
            return response;
        }

        protected void adjustContentType(ContainerResponse response) {
            MediaType newMt = new MediaType("application", "json", ImmutableMap
                    .<String, String>builder()
                    .putAll(response.getMediaType().getParameters())
                    .put(MediaTypes.SCHEMA_ATTRIBUTE, schema)
                    .build());
            response.getHttpHeaders().putSingle(HttpHeaders.CONTENT_TYPE, newMt);
        }
    }

    private class ValidatingWriter implements ContainerResponseWriter {
        private final ContainerResponseWriter crw;
        private ByteArrayOutputStream baos;
        private OutputStream crwout;
        private MediaType mediaType;
        private String schema;
        private MultivaluedMap<String, Object> httpHeaders;

        ValidatingWriter(ContainerResponseWriter crw, String schema) {
            this.crw = crw;
            this.schema = schema;
        }

        @Override
        public OutputStream writeStatusAndHeaders(long contentLength,
                                                  ContainerResponse response)
                throws IOException {
            this.mediaType = response.getMediaType();
            this.crwout = crw.writeStatusAndHeaders(contentLength, response);
            this.baos = new ByteArrayOutputStream();
            this.httpHeaders = response.getHttpHeaders();
            return this.baos;
        }

        @Override
        public void finish() throws IOException {
            byte[] bytes = this.baos.toByteArray();
            ReaderWriter.writeTo(new ByteArrayInputStream(bytes), this.crwout);
            this.crw.finish();
            String contentEncoding = (String) httpHeaders
                    .getFirst(HttpHeaders.CONTENT_ENCODING);
            if (contentEncoding != null && contentEncoding.equals("gzip")) {
                validate(gunzip(bytes));
            } else {
                validate(bytes);
            }
        }

        protected byte[] gunzip(byte[] bytes) throws IOException {
            GZIPInputStream gzin = null;
            ByteArrayInputStream bain = null;
            ByteArrayOutputStream out = null;
            try {
                bain = new ByteArrayInputStream(bytes);
                out = new ByteArrayOutputStream();
                gzin = new GZIPInputStream(bain);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = gzin.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
                return out.toByteArray();
            } finally {
                Closeables.closeQuietly(bain);
                Closeables.closeQuietly(gzin);
                Closeables.closeQuietly(out);
            }
        }

        protected void validate(byte[] bytes) throws IOException,
                                                     WebApplicationException {
            String entity =
                    new String(bytes, ReaderWriter.getCharset(mediaType));
            try {
                JSONSchemaResourceFilterFactory.this.validate(entity, schema);
            } catch (JSONValidationException v) {
                log.error("Created invalid response: Error:\n" +
                          writer.writeValueAsString(v.getError()) +
                          "\nGenerated Response:\n" + entity + "\n", v);
            } catch (ValidationException v) {
                log.error("Created invalid response: Error:\n" +
                          v.getMessage() + "\nGenerated Response:\n" +
                          entity + "\n", v);
                throw new WebApplicationException(v, Status.INTERNAL_SERVER_ERROR);
            }
        }
    }
}