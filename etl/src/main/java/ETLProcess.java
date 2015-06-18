import dataSetDump.DatasetDump;
import entity.Cloner;
import loader.FusekiLoader;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public interface ETLProcess {

    // Extract

    public Cloner getCloner();

    // Transform

    public DatasetDump getDataSetDump();

    // Load

    public FusekiLoader getDumpWriter();


}
