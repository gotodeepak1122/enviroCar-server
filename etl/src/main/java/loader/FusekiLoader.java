package loader;

import com.hp.hpl.jena.query.DatasetAccessor;
import com.hp.hpl.jena.query.DatasetAccessorFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import constants.DBNames;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.rest.encoding.rdf.RDFLinker;
import org.envirocar.server.rest.resources.RootResource;
import org.envirocar.server.rest.resources.TracksResource;

import java.net.URI;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public class FusekiLoader implements TripleStoreLoader {

    public static void putIntoFuseki(Model model) {
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


}