import Loader.DataLoader;
import dataSetDump.DatasetDump;
import entity.Cloner;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public interface ETLProcess {

    // Extract

    public Cloner getCloner();

    // Transform

    public DatasetDump getdatasetdump();

    // Load

    public DataLoader getDumpWiter();


}
