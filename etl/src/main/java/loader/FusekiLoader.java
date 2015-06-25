package loader;

import com.hp.hpl.jena.query.DatasetAccessor;
import com.hp.hpl.jena.query.DatasetAccessorFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import constants.DBNames;
import dataSetDump.POJOEntities.MeasurementPOJO;
import dataSetDump.POJOEntities.TrackPOJO;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.rest.encoding.rdf.vocab.DCTerms;
import org.envirocar.server.rest.encoding.rdf.vocab.DUL;
import org.envirocar.server.rest.resources.MeasurementsResource;
import org.envirocar.server.rest.resources.RootResource;
import org.envirocar.server.rest.resources.TracksResource;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public class FusekiLoader implements TripleStoreLoader {

    public void putIntoFuseki(Model model) {
        String serviceURI = "http://localhost:3030/envirocar/data";
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

    public Model encodeTrack(TrackPOJO track) throws Exception {
        Model m = ModelFactory.createDefaultModel();
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


}