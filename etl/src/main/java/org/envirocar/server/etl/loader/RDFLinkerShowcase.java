/*
 * Copyright (C) 2013 The enviroCar project
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
package org.envirocar.server.etl.loader;

import com.google.inject.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import org.envirocar.server.core.entities.EntityFactory;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.core.exception.GeometryConverterException;
import org.envirocar.server.core.guice.CoreModule;
import org.envirocar.server.core.guice.EventModule;
import org.envirocar.server.core.guice.UpdaterModule;
import org.envirocar.server.core.guice.ValidatorModule;
import org.envirocar.server.mongo.guice.*;
import org.envirocar.server.rest.encoding.rdf.RDFLinker;
import org.envirocar.server.rest.guice.DefaultRDFLinkerModule;
import org.envirocar.server.rest.resources.RootResource;
import org.envirocar.server.rest.resources.TracksResource;
import org.envirocar.server.rest.rights.AccessRights;
import org.envirocar.server.rest.rights.NonRestrictiveRights;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Set;


/**
 * @author deepaknair on 20/06/15.
 */


public class RDFLinkerShowcase {

    private final Set<RDFLinker<Track>> linkers;
    private final AccessRights accessRights;
    private final Provider<UriBuilder> uriBuilder;
    private final EntityFactory entityFactory;

    @Inject
    public RDFLinkerShowcase(Set<RDFLinker<Track>> linkers,
                             AccessRights accessRights,
                             Provider<UriBuilder> uriBuilder,
                             EntityFactory entityFactory) {
        this.linkers = linkers;
        this.accessRights = accessRights;
        this.uriBuilder = uriBuilder;
        this.entityFactory = entityFactory;
    }

    public static void main(String[] args) throws IOException, GeometryConverterException {
        RDFLinkerShowcase showcase = Guice.createInjector(new RDFModule())
                .getInstance(RDFLinkerShowcase.class);

        // Track track = showcase.createTrack();
        // Model model = showcase.encodeTrack(track);
        //  showcase.writeTo(model, System.out);
    }

    public static Model convertEntityToRDF(Track trackToBeConverted) {
        Model convertedModel = ModelFactory.createDefaultModel();
        RDFLinkerShowcase showcase = Guice.createInjector(new RDFModule())
                .getInstance(RDFLinkerShowcase.class);
        return showcase.encodeTrack(trackToBeConverted);
    }



    public Model encodeTrack(Track track) {
        Model m = ModelFactory.createDefaultModel();
        URI trackURI = uriBuilder.get()
                .path(RootResource.class)
                .path(RootResource.TRACKS)
                .path(TracksResource.TRACK)
                .build(track.getIdentifier());
        Resource r = m.createResource(trackURI.toASCIIString());
        for (RDFLinker<Track> linker : this.linkers) {
            linker.link(m, track, this.accessRights, r, this.uriBuilder);
        }
        return m;

    }

    public void writeTo(Model m, OutputStream out) throws IOException {
        m.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
        m.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        m.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        String base = this.uriBuilder.get().build().toASCIIString();
        m.write(out, "TTL", base);
        out.flush();
    }

    private static class RDFModule extends AbstractModule {

        @Override
        protected void configure() {
            install(new DefaultRDFLinkerModule());
            install(new CoreModule());
            install(new MongoModule());
            install(new UpdaterModule());
            install(new ValidatorModule());
            install(new EventModule());
            install(new MongoDaoModule());
            install(new MongoConnectionModule());
            install(new MongoConverterModule());
            install(new MongoMappedClassesModule());
        }

        @Provides
        public AccessRights accessRights() {
            return new NonRestrictiveRights();
        }

        @Provides
        public UriBuilder uriBuilder() {
            return UriBuilder.fromPath("http://envirocar.org/api/stable/");
        }
    }
}
