package entity;

import constants.ExecutionStatus;
import dataSetDump.POJODatasetDump;
import mongoOperations.MongoReader;
import org.envirocar.server.core.exception.GeometryConverterException;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;

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


    public POJODatasetDump cloneIntoMemory() throws GeometryConverterException, UnknownHostException {

        /**
         * Dump is being populated
         */
        MongoCloner testcloner = new MongoCloner();
        POJODatasetDump pojoDatasetDump = new POJODatasetDump();
        pojoDatasetDump.measurementPOJOList = testcloner.mongoReader.getAllMeasurements();
        pojoDatasetDump.userPOJOList = testcloner.mongoReader.getAllUsers();
        pojoDatasetDump.trackPOJOList = testcloner.mongoReader.getAllTracks();
        pojoDatasetDump.phenomenonPOJOList = testcloner.mongoReader.getAllPhenomenon();
        pojoDatasetDump.sensorPOJOList = testcloner.mongoReader.getAllSensors();

        return pojoDatasetDump;
    }

    @Override
    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }


}
