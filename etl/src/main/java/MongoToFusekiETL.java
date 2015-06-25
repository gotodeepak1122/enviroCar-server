import dataSetDump.DatasetDump;
import dataSetDump.POJODatasetDump;
import dataSetDump.POJOEntities.TrackPOJO;
import entity.Cloner;
import entity.MongoCloner;
import loader.FusekiLoader;

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
     * Early release Loader extractor and Loader are work with all and loader can transform tracks from the dataset dump
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        MongoToFusekiETL etl = new MongoToFusekiETL();
        POJODatasetDump pojoDatasetDump = etl.mongoCloner.cloneIntoMemory();
        etl.dataSetDump = pojoDatasetDump;
        for (TrackPOJO trackPOJO1 : etl.dataSetDump.trackPOJOList) {
            etl.fusekiLoader.putIntoFuseki(etl.fusekiLoader.encodeTrack(trackPOJO1));
        }

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
