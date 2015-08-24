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
package org.envirocar.server.etl.utils;

import com.google.inject.Provider;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.vividsolutions.jts.geom.Point;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.etl.dataSetDump.POJOEntities.*;
import org.envirocar.server.rest.encoding.rdf.linker.*;
import org.envirocar.server.rest.encoding.rdf.vocab.*;
import org.envirocar.server.rest.resources.*;
import org.envirocar.server.rest.rights.NonRestrictiveRights;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author deepaknair on 07/08/15.
 */
public class RDFUtils {

    private static String root = "https://envirocar.org/api/stable/";


    public static URI getTrackURI(Track track) {
        URI trackURI = UriBuilder.fromPath(root)
                .path(RootResource.class)
                .path(RootResource.TRACKS)
                .path(TracksResource.TRACK)
                .build(track.getIdentifier());
        return trackURI;

    }

    public static Model encodeTracks(List<TrackPOJO> trackPOJOList) throws Exception {
        Model m = ModelFactory.createDefaultModel();
        for (TrackPOJO trackPOJO : trackPOJOList) {
            encodeTrack(trackPOJO, m);
        }
        return m;
    }

    public static Model encodeMeasurements(List<MeasurementPOJO> measurementPOJOList) throws Exception {
        Model m = ModelFactory.createDefaultModel();
        for (MeasurementPOJO measurementPOJO : measurementPOJOList) {
            encodeMeasurement(measurementPOJO, m);
        }
        return m;
    }

    public static Model encodeUsers(List<UserPOJO> userPOJOList) throws Exception {
        Model m = ModelFactory.createDefaultModel();
        for (UserPOJO userPOJO : userPOJOList) {

            encodeUser(userPOJO, m);
        }
        return m;
    }

    public static Model encodePhenomenons(List<PhenomenonPOJO> phenomenonPOJOList) throws Exception {
        Model m = ModelFactory.createDefaultModel();
        for (PhenomenonPOJO phenomenonPOJO : phenomenonPOJOList) {
            encodePhenomenon(phenomenonPOJO, m);
        }
        return m;
    }


    public static Model encodeSensors(List<SensorPOJO> sensorPOJOList) {
        Model m = ModelFactory.createDefaultModel();
        for (SensorPOJO sensorPOJO : sensorPOJOList) {
            encodeSensor(m, sensorPOJO);
        }
        return m;
    }



    /**
     * Encodes track POJO into RDF
     * Conversion cheat sheet Track->measurement (dul:hasMember) ; Track->licences/odbl ( dul:hasRights)
     *
     * @param track
     * @param m
     * @return
     * @throws Exception
     */

    public static Model encodeTrack(TrackPOJO track, Model m) throws Exception {
        URI trackURI = UriBuilder.fromPath(root)
                .path(RootResource.class)
                .path(RootResource.TRACKS)
                .path(TracksResource.TRACK)
                .build(track.getIdentifier());
        String base = trackURI.toASCIIString();
        Resource r = m.createResource(base);
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

    /**
     * This class shows the process involved in converting MeasurementPOJO into RDF linkers
     *
     * @param measurementPOJO
     * @return
     */

    public static void encodeMeasurement(MeasurementPOJO measurementPOJO, Model measurementModel) {

        URI measurementURI = UriBuilder.fromPath(root)
                .path(RootResource.class)
                .path(RootResource.MEASUREMENTS)
                .path(MeasurementsResource.MEASUREMENT)
                .build(measurementPOJO.getIdentifier());

        String measurementBaseURI = measurementURI.toASCIIString();

        Resource r = measurementModel.createResource(measurementBaseURI);

        //DC Terms Linkage

        measurementModel.setNsPrefix(DCTerms.PREFIX, DCTerms.URI);
        r.addProperty(DCTerms.rights, DCTermsLinker.ODBL_URL);

        //DULL linkage

        measurementModel.setNsPrefix(DUL.PREFIX, DUL.URI);
        r.addProperty(DUL.isMemberOf, getTrackURI(measurementPOJO.getTrack()).toASCIIString());

        // SSN Linkage

// TODO:

        // W3C Geo linkage

        if (measurementPOJO.getGeometry() instanceof Point) {
            measurementModel.setNsPrefix(W3CGeo.PREFIX, W3CGeo.URI);
            Point p = (Point) measurementPOJO.getGeometry();
            r.addLiteral(W3CGeo.lat, p.getY())
                    .addLiteral(W3CGeo.lon, p.getX());

        }

    }

    public static void encodeUser(UserPOJO userPOJO, Model userModel) {


        userModel.setNsPrefix(UserVCardLinker.VCARD_PREFIX, VCARD.getURI());
        userModel.setNsPrefix(DCTerms.PREFIX, DCTerms.URI);
        userModel.setNsPrefix(UserFOAFLinker.PREFIX, FOAF.NS);


        String userURI = UriBuilder.fromPath(root)
                .path(RootResource.class)
                .path(RootResource.USERS)
                .path(UsersResource.USER)
                .build(userPOJO.getName())
                .toASCIIString();

        NonRestrictiveRights rights = new NonRestrictiveRights();
        Resource r = userModel.createResource(userURI);

        //VCard Linkage
        r.addProperty(VCARD.EMAIL, userPOJO.getMail())
                .addProperty(VCARD.NICKNAME, userPOJO.getName());

        //FOAF Linkage

        r.addProperty(RDF.type, FOAF.Person);
        r.addLiteral(FOAF.nick, userPOJO.getName());
        if (userPOJO.hasFirstName() && rights.canSeeFirstNameOf(userPOJO)) {
            r.addLiteral(FOAF.firstName, userPOJO.getFirstName());
            r.addLiteral(FOAF.givenname, userPOJO.getFirstName());
        }
        if (userPOJO.hasLastName() && rights.canSeeLastNameOf(userPOJO)) {
            r.addLiteral(FOAF.surname, userPOJO.getLastName());
            r.addLiteral(FOAF.family_name, userPOJO.getLastName());
        }
        if (userPOJO.hasDayOfBirth() && rights.canSeeDayOfBirthOf(userPOJO)) {
            r.addLiteral(FOAF.birthday, userPOJO.getDayOfBirth());
        }
        if (userPOJO.hasUrl() && rights.canSeeUrlOf(userPOJO)) {
            r.addProperty(FOAF.homepage, userModel.createResource(userPOJO.getUrl()
                    .toString()));
        }
        if (userPOJO.hasGender() && rights.canSeeGenderOf(userPOJO)) {
            r.addLiteral(FOAF.gender, userPOJO.getGender().toString().toLowerCase());
        }
        if (rights.canSeeAvatarOf(userPOJO)) {
            r.addProperty(FOAF.img, userModel.createResource(UriBuilder.fromUri(r
                    .getURI()).path(UserResource.AVATAR).build().toASCIIString()));
        }
        if (userPOJO.hasMail() && rights.canSeeMailOf(userPOJO)) {
            r.addLiteral(FOAF.mbox, "mailto:" + userPOJO.getMail());
        }
        r.addProperty(DCTerms.rights, DCTermsLinker.ODBL_URL);

    }

    public static void encodePhenomenon(PhenomenonPOJO phenomenonPOJO, Model model) {

        String phenomenonURI = UriBuilder.fromPath(root)
                .path(RootResource.class)
                .path(RootResource.PHENOMENONS)
                .path(PhenomenonsResource.PHENOMENON)
                .build(phenomenonPOJO.getName())
                .toASCIIString();


        model.setNsPrefix(DCTerms.PREFIX, DCTerms.URI);
        model.setNsPrefix(SSN.PREFIX, SSN.URI);
        model.setNsPrefix(DUL.PREFIX, DUL.URI);
        //DC Terms


        Resource resource = model.createResource(phenomenonURI);
        linkDCTerms(model, resource);

        //SSN

        resource.addProperty(RDF.type, SSN.Property);
        Resource unit = model.createResource(fragment(resource,
                MeasurementSSNLinker.UNIT_FRAGMENT));
        unit.addProperty(RDF.type, DUL.UnitOfMeasure);
        unit.addLiteral(RDFS.comment, phenomenonPOJO.getUnit());

        // DBPedia
        DBPediaPhenomenonLinker dbPediaPhenomenonLinker = new DBPediaPhenomenonLinker();
        Provider<UriBuilder> uri = null;
        dbPediaPhenomenonLinker.link(model, phenomenonPOJO, new NonRestrictiveRights(), resource, uri);


        // EEAPhenomenonLinker

        EEAPhenomenonLinker eeaPhenomenonLinker = new EEAPhenomenonLinker();
        Provider<UriBuilder> temporaryURI = null;
        eeaPhenomenonLinker.link(model, phenomenonPOJO, new NonRestrictiveRights(), resource, temporaryURI);


    }

    public static void encodeSensor(Model m, SensorPOJO sensorPOJO) {


        String CONSTRUCTION_YEAR_PROPERTY = "constructionYear";
        String FUEL_TYPE_PROPERTY = "fuelType";
        String MANUFACTURER_PROPERTY = "manufacturer";
        String MODEL_PROPERTY = "model";
        String FUEL_TYPE_DIESEL = "diesel";
        String FUEL_TYPE_GASOLINE = "gasoline";
        String FUEL_TYPE_BIODIESEL = "biodiesel";
        String FUEL_TYPE_KEROSENE = "kerosene";
        String CAR_TYPE = "car";


        String sensorURI = UriBuilder.fromPath(root)
                .path(RootResource.class)
                .path(RootResource.SENSORS)
                .path(SensorsResource.SENSOR)
                .build(sensorPOJO.getIdentifier())
                .toASCIIString();


        m.setNsPrefix(DCTerms.PREFIX, DCTerms.URI);


        Resource sensorResource = m.createResource(sensorURI);

        // DC Terms
        linkDCTerms(m, sensorResource);


        // SSN

        Resource sensorDULL = m.createResource(fragment(sensorResource, "sensor"));
        sensorDULL.addProperty(RDF.type, SSN.Sensor);


        if (sensorPOJO.hasType() && sensorPOJO.getType().equals(CAR_TYPE)) {
            if (sensorPOJO.hasProperties()) {
                final Map<String, Object> p = sensorPOJO.getProperties();
                //Subclass of  http://purl.org/goodrelations/v1#ProductOrService
                m.setNsPrefix(VSO.PREFIX, VSO.URI);
                m.setNsPrefix(GoodRelations.PREFIX, GoodRelations.URI);
                m.setNsPrefix(DBPedia.PREFIX, DBPedia.URI);
                sensorResource.addProperty(RDF.type, VSO.Automobile);
                addFuelType(p, m, sensorResource);
                addContructionYear(p, sensorResource);

                final String manufacturer = (String) p
                        .get(MANUFACTURER_PROPERTY);
                if (manufacturer != null) {
                    sensorResource.addLiteral(GoodRelations.hasManufacturer,
                            manufacturer);
                    String model = (String) p.get(MODEL_PROPERTY);
                    if (model != null) {
                        final String hasMakeAndModel =
                                manufacturer + "_" + model;
                        sensorResource.addLiteral(GoodRelations.hasMakeAndModel,
                                hasMakeAndModel);
                    }
                }
            }
        }


    }

    private static void addFuelType(final Map<String, Object> p, Model m, final Resource sensor) {
        final String fuelType = (String) p.get(SensorVSOLinker.FUEL_TYPE_PROPERTY);
        if (fuelType != null) {
            if (fuelType.equals(SensorVSOLinker.FUEL_TYPE_DIESEL)) {
                sensor.addProperty(VSO.fuelType, DBPedia.DBPEDIA_DIESEL);
            } else if (fuelType.equals(SensorVSOLinker.FUEL_TYPE_GASOLINE)) {
                sensor.addProperty(VSO.fuelType, DBPedia.DBPEDIA_GASOLINE);
            } else if (fuelType.equals(SensorVSOLinker.FUEL_TYPE_BIODIESEL)) {
                sensor.addProperty(VSO.fuelType, DBPedia.DBPEDIA_BIODIESEL);
            } else if (fuelType.equals(SensorVSOLinker.FUEL_TYPE_KEROSENE)) {
                sensor.addProperty(VSO.fuelType, DBPedia.DBPEDIA_KEROSENE);
            }
        }
    }

    private static void addContructionYear(
            final Map<String, Object> p,
            final Resource sensor) {
        Number year = (Number) p.get(SensorVSOLinker.CONSTRUCTION_YEAR_PROPERTY);
        if (year != null) {
            final String modelDate = year.intValue() + "-01-01";
            sensor.addProperty(VSO.modelDate, modelDate, XSDDatatype.XSDdate);
        }
    }

    private static String fragment(Resource resource, String fragment) {
        return UriBuilder.fromUri(resource.getURI())
                .fragment(fragment)
                .build()
                .toASCIIString();
    }

    private static void linkDCTerms(Model modelToBeLinked, Resource resource) {
        resource.addProperty(DCTerms.rights, DCTermsLinker.ODBL_URL);

    }

   /* public static void encodeActivities(ActivityPOJO activityPOJO ,Model activityModel){
        URI activityURI =

    }*/

    private void encodeActivity(Model m, ActivityPOJO activityPOJO) {

    }


}
