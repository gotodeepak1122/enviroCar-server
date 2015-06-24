package entity;

import constants.ExecutionStatus;
import dataSetDump.POJODatasetDump;
import dataSetDump.POJOEntities.MeasurementPOJO;
import dataSetDump.POJOEntities.TrackPOJO;
import mongoOperations.MongoReader;

import java.net.UnknownHostException;
import java.util.*;

/**
 * @author deepaknair on 17/06/15 AD.
 */

/**
 * entity which reads dumps from Mongo into a List of objects in Java
 */
public class MongoCloner implements DBCloner {

    private Set<String> supportedCollections;
    private MongoReader mongoReader;
    private Date LastExecutionTime;
    private ExecutionStatus executionStatus;


    public MongoCloner() throws UnknownHostException {
        this.setExecutionStatus(ExecutionStatus.IDLE);
        this.mongoReader = new MongoReader();
    }


    @Override
    public Date getLastExecutionTime() {
        return this.LastExecutionTime;
    }


    public POJODatasetDump cloneIntoMemory() throws Exception {

        /**
         * Dump is being populated
         */
        MongoCloner testcloner = new MongoCloner();
        POJODatasetDump pojoDatasetDump = new POJODatasetDump();
        pojoDatasetDump.measurementPOJOList = testcloner.mongoReader.getAllMeasurements();
        pojoDatasetDump.userPOJOList = testcloner.mongoReader.getAllUsers();
        pojoDatasetDump.trackPOJOList = testcloner.mongoReader.getAllTracks();
        populateTracksWithMeasurements(pojoDatasetDump);
        pojoDatasetDump.phenomenonPOJOList = testcloner.mongoReader.getAllPhenomenon();
        pojoDatasetDump.sensorPOJOList = testcloner.mongoReader.getAllSensors();

        return pojoDatasetDump;
    }

    /**
     * Iterated through the measurements and runs through their tracks
     * run this only after all the measurements have been populated
     */

    private void populateTracksWithMeasurements(POJODatasetDump pojoDatasetDump) throws Exception {
        // using a map because it is faster than iterating over each measurement to populate one track O(n)2

        Map<String, ArrayList<MeasurementPOJO>> trackToMeasurement = new HashMap<String, ArrayList<MeasurementPOJO>>();
        for (MeasurementPOJO measurement : pojoDatasetDump.measurementPOJOList) {
            String trackID = measurement.getTrack().getIdentifier();

            if (trackToMeasurement.containsKey(trackID)) {
                ArrayList<MeasurementPOJO> temp = trackToMeasurement.get(trackID);
                temp.add(measurement);
                trackToMeasurement.put(trackID, temp);
            } else {
                ArrayList<MeasurementPOJO> arrayList = new ArrayList<MeasurementPOJO>();
                arrayList.add(measurement);
                trackToMeasurement.put(trackID, arrayList);

            }
            for (TrackPOJO trackPOJO : pojoDatasetDump.trackPOJOList) {
                ArrayList<MeasurementPOJO> trackMeasurements = trackToMeasurement.get(trackPOJO.getIdentifier());
                trackPOJO.setMeasurementPOJOs(trackMeasurements);
            }
        }


    }


    @Override
    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }


}
