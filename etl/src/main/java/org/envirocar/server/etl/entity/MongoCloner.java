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
package org.envirocar.server.etl.entity;

import org.envirocar.server.etl.constants.ExecutionStatus;
import org.envirocar.server.etl.dataSetDump.POJODatasetDump;
import org.envirocar.server.etl.dataSetDump.POJOEntities.MeasurementPOJO;
import org.envirocar.server.etl.dataSetDump.POJOEntities.TrackPOJO;
import org.envirocar.server.etl.utils.MongoUtils;

import java.net.UnknownHostException;
import java.util.*;

/**
 * @author deepaknair on 17/06/15 AD.
 */

/**
 * org.envirocar.server.etl.entity which reads dumps from Mongo into a List of objects in Java
 */
public class MongoCloner implements DBCloner {

    private Set<String> supportedCollections;
    private MongoUtils mongoUtils;
    private Date LastExecutionTime;
    private ExecutionStatus executionStatus;


    public MongoCloner() throws UnknownHostException {
        this.setExecutionStatus(ExecutionStatus.IDLE);
        this.mongoUtils = new MongoUtils();
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
        pojoDatasetDump.userPOJOList = testcloner.mongoUtils.getAllUsers();
        pojoDatasetDump.measurementPOJOList = testcloner.mongoUtils.getAllMeasurements();
        pojoDatasetDump.trackPOJOList = testcloner.mongoUtils.getAllTracks();
        populateTracksWithMeasurements(pojoDatasetDump);
        pojoDatasetDump.phenomenonPOJOList = testcloner.mongoUtils.getAllPhenomenon();
        pojoDatasetDump.sensorPOJOList = testcloner.mongoUtils.getAllSensors();

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
