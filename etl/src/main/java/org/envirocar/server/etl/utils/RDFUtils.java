package org.envirocar.server.etl.utils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.vividsolutions.jts.geom.Point;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.etl.dataSetDump.POJOEntities.MeasurementPOJO;
import org.envirocar.server.etl.dataSetDump.POJOEntities.TrackPOJO;
import org.envirocar.server.etl.dataSetDump.POJOEntities.UserPOJO;
import org.envirocar.server.rest.encoding.rdf.linker.DCTermsLinker;
import org.envirocar.server.rest.encoding.rdf.linker.UserFOAFLinker;
import org.envirocar.server.rest.encoding.rdf.linker.UserVCardLinker;
import org.envirocar.server.rest.encoding.rdf.vocab.DCTerms;
import org.envirocar.server.rest.encoding.rdf.vocab.DUL;
import org.envirocar.server.rest.encoding.rdf.vocab.W3CGeo;
import org.envirocar.server.rest.resources.*;
import org.envirocar.server.rest.rights.NonRestrictiveRights;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

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
        System.out.println(userPOJOList.get(0).getName());
        for (UserPOJO userPOJO : userPOJOList) {

            encodeUser(userPOJO, m);
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
        r.addProperty(VCARD.EMAIL, userPOJO.getMail())
                .addProperty(VCARD.NICKNAME, userPOJO.getName());
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


}
