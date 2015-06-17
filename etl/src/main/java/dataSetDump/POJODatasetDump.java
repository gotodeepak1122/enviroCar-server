package dataSetDump;

import dataSetDump.POJOEntities.*;

import java.util.List;

/**
 * @author deepaknair on 17/06/15 AD.
 *         A dataset dump which loads data into POJO's
 */
public class POJODatasetDump implements DatasetDump {

    List<MeasurementPOJO> measurementPOJOList;
    List<TrackPOJO> trackPOJOList;
    List<SensorPOJO> sensorPOJOList;
    List<BadgePOJO> badgePOJOList;
    List<PhenomenonPOJO> phenomenonPOJOList;
    List<UserPOJO> userPOJOList;
}
