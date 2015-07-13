package org.envirocar.server.etl;/*
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

import org.envirocar.server.etl.dataSetDump.DatasetDump;
import org.envirocar.server.etl.dataSetDump.POJODatasetDump;
import org.envirocar.server.etl.entity.Cloner;
import org.envirocar.server.etl.entity.MongoCloner;
import org.envirocar.server.etl.loader.FusekiLoader;

import java.net.UnknownHostException;

/**
 * @author deepaknair on 18/06/15 AD.
 * an implementation of an ETL process which runs from Mongo source to Fuseki Destination
 */


public class MongoToFusekiETL implements ETLProcess {

    MongoCloner mongoCloner;
    POJODatasetDump dataSetDump;
    FusekiLoader fusekiLoader;

    MongoToFusekiETL() throws UnknownHostException {
        this.mongoCloner = new MongoCloner();
        this.fusekiLoader = new FusekiLoader();
    }

    /**
     * Early release Loader extractor and Loader are work with all and org.envirocar.server.etl.loader can transform tracks from the dataset dump
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        MongoToFusekiETL etl = new MongoToFusekiETL();
        POJODatasetDump pojoDatasetDump = etl.mongoCloner.cloneIntoMemory();
        etl.dataSetDump = pojoDatasetDump;
        etl.fusekiLoader.putIntoFuseki(FusekiLoader.encodeTracks(etl.dataSetDump.trackPOJOList));


    }

    @Override
    public Cloner getCloner() {
        return this.mongoCloner;
    }

    @Override
    public DatasetDump getDataSetDump() {
        return this.dataSetDump;
    }

    @Override
    public FusekiLoader getDumpWriter() {
        return this.fusekiLoader;
    }
}
