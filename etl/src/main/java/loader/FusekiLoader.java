package loader;

import com.hp.hpl.jena.rdf.model.Model;
import constants.DBNames;
import org.envirocar.server.core.entities.Track;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public class FusekiLoader implements TripleStoreLoader {

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


}