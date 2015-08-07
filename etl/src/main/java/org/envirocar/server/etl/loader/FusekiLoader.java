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
import org.envirocar.server.etl.constants.DBNames;
import org.envirocar.server.etl.dataSetDump.POJOEntities.TrackPOJO;
import org.envirocar.server.etl.utils.ConfigReader;
import org.envirocar.server.etl.utils.RDFUtils;

import java.io.IOException;
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
        m = RDFUtils.encodeTracks(trackPOJOList);
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

    // Implemented example by Christian

    /**public Model convertEntityToRDF(Track trackToBeConverted) {
     Model convertedModel = RDFLinkerShowcase.convertEntityToRDF(trackToBeConverted);
     return convertedModel;
     }*/



    /**
     * Overridden method for a Triple Store Loader
     */
    @Override
    public String getTargetLoader() {
        return DBNames.APACHE_JENA_FUSEKI;
    }







}