import dataSetDump.DatasetDump;
import dataSetDump.POJODatasetDump;
import entity.Cloner;
import entity.MongoCloner;
import loader.FusekiLoader;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.core.exception.GeometryConverterException;

import java.net.UnknownHostException;

/**
 * @author deepaknair on 18/06/15 AD.
 */
public class MongoToFusekiETL implements ETLProcess {

    MongoCloner mongoCloner;
    POJODatasetDump dataSetDump;
    FusekiLoader fusekiLoader;

    MongoToFusekiETL() throws UnknownHostException {
        this.mongoCloner = new MongoCloner();
        this.fusekiLoader = new FusekiLoader();
    }

    public static void main(String[] args) throws GeometryConverterException, UnknownHostException {
        MongoToFusekiETL etl = new MongoToFusekiETL();
        POJODatasetDump pojoDatasetDump = etl.mongoCloner.cloneIntoMemory();
        etl.dataSetDump = pojoDatasetDump;
        for (Track track : pojoDatasetDump.trackPOJOList) {
            System.out.println(track.toString());
            FusekiLoader.putIntoFuseki(etl.fusekiLoader.convertEntityToRDF(track));
            etl.fusekiLoader.convertEntityToRDF(track);
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
