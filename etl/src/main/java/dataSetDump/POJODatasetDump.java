package dataSetDump;

import dataSetDump.POJOEntities.*;

import java.util.List;

/**
 * @author deepaknair on 17/06/15 AD.
 *         A dataset dump which loads data into POJO's
 *         Didnt think getter and setter would be required because it just functions as an intermittent data exchange format
 */


public class POJODatasetDump implements DatasetDump {

    public List<MeasurementPOJO> measurementPOJOList;
    public List<TrackPOJO> trackPOJOList;
    public List<SensorPOJO> sensorPOJOList;
    public List<BadgePOJO> badgePOJOList;
    public List<PhenomenonPOJO> phenomenonPOJOList;
    public List<UserPOJO> userPOJOList;
}
