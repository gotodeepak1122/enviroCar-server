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

import com.hp.hpl.jena.query.DatasetAccessor;
import com.hp.hpl.jena.query.DatasetAccessorFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.vividsolutions.jts.geom.Point;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.etl.constants.DBNames;
import org.envirocar.server.etl.dataSetDump.POJOEntities.*;
import org.envirocar.server.etl.utils.ConfigReader;
import org.envirocar.server.rest.encoding.rdf.vocab.DCTerms;
import org.envirocar.server.rest.encoding.rdf.vocab.DUL;
import org.envirocar.server.rest.encoding.rdf.vocab.W3CGeo;
import org.envirocar.server.rest.resources.MeasurementsResource;
import org.envirocar.server.rest.resources.RootResource;
import org.envirocar.server.rest.resources.TracksResource;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public class FusekiLoader implements TripleStoreLoader {

    /**
     * Function to convert track entities into RDF models
     *
     * @return
     * @throws Exception
     */

    public static Model encodeTracks(List<TrackPOJO> trackPOJOList) throws Exception {
        Model m = ModelFactory.createDefaultModel();
        for (TrackPOJO trackPOJO : trackPOJOList) {
            encodeTrack(trackPOJO, m);
        }
        return m;
    }

    public static Model encodeTrack(TrackPOJO track, Model m) throws Exception {
        String root = "https://envirocar.org/api/stable/";
        URI trackURI = UriBuilder.fromPath(root)
                .path(RootResource.class)
                .path(RootResource.TRACKS)
                .path(TracksResource.TRACK)
                .build(track.getIdentifier());
        String base = trackURI.toASCIIString();
        // System.out.println(base);
        Resource r = m.createResource(base);
        Model n = ModelFactory.createDefaultModel();
        r.addProperty(RDF.type, DUL.Collection);
        m.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
        m.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        m.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        m.setNsPrefix(DUL.PREFIX, DUL.URI);
        m.setNsPrefix(DCTerms.PREFIX, DCTerms.URI);

        for (MeasurementPOJO measurementPOJO : track.getMeasurementPOJOs()) {
            URI measurementURI = UriBuilder.fromPath(root)
                    .path(RootResource.class)
                    .path(RootResource.MEASUREMENTS)
                    .path(MeasurementsResource.MEASUREMENT)
                    .build(measurementPOJO.getIdentifier());
            Resource measurementResource = m.createResource(measurementURI.toASCIIString());
            r.addProperty(DUL.hasMember, measurementResource);


        }

        r.addProperty(DCTerms.rights, "http://opendatacommons.org/licenses/odbl/");

        m.write(System.out, "TTL", root);


        return m;


    }

    public void putIntoFuseki(Model model) throws IOException {

        // Load the fuseki hosted url from configs
        ConfigReader.read();


        String serviceURI = ConfigReader.FUSEKI_URL + "/enviroCar/data";
        DatasetAccessorFactory factory = new DatasetAccessorFactory();
        DatasetAccessor accessor;
        accessor = DatasetAccessorFactory.createHTTP(serviceURI);
        accessor.putModel(model);

    }

    /**
     * Overridden method for a Triple Store Loader
     */
    @Override
    public String getTargetLoader() {
        return DBNames.APACHE_JENA_FUSEKI;
    }

    public Model convertEntityToRDF(Track trackToBeConverted) {
        Model convertedModel = RDFLinkerShowcase.convertEntityToRDF(trackToBeConverted);
        return convertedModel;
    }

    public void encodeMeasurement(MeasurementPOJO measurementPOJO) {
        String root = "https://envirocar.org/api/stable/";


        // create model
        Model m = ModelFactory.createDefaultModel();
        Resource r = m.createResource();
        // set prefixes
        m.setNsPrefix(W3CGeo.PREFIX, W3CGeo.URI);
        String embeddedTrackURI = UriBuilder.fromPath(root)
                .path(RootResource.class)
                .path(RootResource.TRACKS)
                .path(TracksResource.TRACK)
                .build(measurementPOJO.getTrack().getIdentifier())
                .toASCIIString();

        // add properties
        if (measurementPOJO.getGeometry() instanceof Point) {
            Point p = (Point) measurementPOJO.getGeometry();
            r.addLiteral(W3CGeo.lat, p.getY())
                    .addLiteral(W3CGeo.lon, p.getX());


            r.addProperty(DUL.isMemberOf, m.createResource(embeddedTrackURI));

            // add ssn measurement linkers

        }

    }

    public void encodeSensors(SensorPOJO sensorPOJO) {

    }

    public void encodeUser(UserPOJO userPOJO) {

    }

    public void encodePhenomenon(PhenomenonPOJO phenomenonPOJO) {

    }

    public void encodeActivity() {

    }






}