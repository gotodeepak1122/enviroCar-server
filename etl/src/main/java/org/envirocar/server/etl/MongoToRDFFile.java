package org.envirocar.server.etl;

import org.envirocar.server.etl.dataSetDump.DatasetDump;
import org.envirocar.server.etl.dataSetDump.POJODatasetDump;
import org.envirocar.server.etl.entity.Cloner;
import org.envirocar.server.etl.entity.MongoCloner;
import org.envirocar.server.etl.loader.FusekiLoader;
import org.envirocar.server.etl.loader.RDFDumpWriter;

import java.net.UnknownHostException;

/**
 * @author deepaknair on 14/07/15
 * Creates an RDF dump of file of all envirocar data , usefuel for updating through sparql protocolo.
 */
public class MongoToRDFFile implements ETLProcess {


    MongoCloner mongoCloner;
    POJODatasetDump dataSetDump;
    RDFDumpWriter rdfDumpWriter;

    MongoToRDFFile() throws UnknownHostException {
        this.mongoCloner = new MongoCloner();
    }

    public static void main(String[] args) throws Exception {
        MongoToRDFFile etl = new MongoToRDFFile();
        etl.dataSetDump = etl.mongoCloner.cloneIntoMemory();
        RDFDumpWriter.writeIntoFile(FusekiLoader.encodeTracks(etl.dataSetDump.trackPOJOList), args[0]);
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
