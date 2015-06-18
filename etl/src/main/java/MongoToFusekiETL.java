import dataSetDump.DatasetDump;
import dataSetDump.POJODatasetDump;
import entity.Cloner;
import entity.MongoCloner;
import loader.FusekiLoader;
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
    }

    public static void main(String[] args) throws GeometryConverterException, UnknownHostException {
        MongoToFusekiETL etl = new MongoToFusekiETL();
        POJODatasetDump pojoDatasetDump = etl.mongoCloner.cloneIntoMemory();
        System.out.println(etl.dataSetDump.userPOJOList.get(0));
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
