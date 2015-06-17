package loader;

import constants.DBNames;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public class FusekiLoader implements TripleStoreLoader {

    /**
     * Overridden methods for a Triple Store Loader
     */
    @Override
    public String getTargetLoader() {
        return DBNames.APACHE_JENA_FUSEKI;
    }

}