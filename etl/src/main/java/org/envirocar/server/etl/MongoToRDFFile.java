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
package org.envirocar.server.etl;

import org.envirocar.server.etl.dataSetDump.DatasetDump;
import org.envirocar.server.etl.dataSetDump.POJODatasetDump;
import org.envirocar.server.etl.entity.Cloner;
import org.envirocar.server.etl.entity.MongoCloner;
import org.envirocar.server.etl.loader.FusekiLoader;
import org.envirocar.server.etl.loader.RDFDumpWriter;
import org.envirocar.server.etl.utils.RDFUtils;

import java.net.UnknownHostException;

/**
 * @author deepaknair on 14/07/15
 * Creates an RDF dump of file of all envirocar data , usefuel for updating through sparql protocolo.
 */
public class MongoToRDFFile implements ETLProcess {


    MongoCloner mongoCloner;
    POJODatasetDump dataSetDump;
    //RDFDumpWriter rdfDumpWriter;

    MongoToRDFFile() throws UnknownHostException {
        this.mongoCloner = new MongoCloner();
    }

    public static void main(String[] args) throws Exception {
        MongoToRDFFile etl = new MongoToRDFFile();
        etl.dataSetDump = etl.mongoCloner.cloneIntoMemory();
        RDFDumpWriter.writeIntoFile(RDFUtils.encodeTracks(etl.dataSetDump.trackPOJOList), "tracks.rdf");
        RDFDumpWriter.writeIntoFile(RDFUtils.encodeMeasurements(etl.dataSetDump.measurementPOJOList), "measurements.rdf");
        RDFDumpWriter.writeIntoFile(RDFUtils.encodeUsers(etl.dataSetDump.userPOJOList), "users.rdf");

    }

    @Override
    public Cloner getCloner() {
        return this.mongoCloner;
    }

    @Override
    public DatasetDump getDataSetDump() {
        return dataSetDump;
    }

    @Override
    public FusekiLoader getDumpWriter() {
        return null;
    }
}
