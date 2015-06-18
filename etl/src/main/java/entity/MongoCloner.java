package entity;

import constants.ExecutionStatus;
import dataSetDump.POJODatasetDump;
import dataSetDump.POJOEntities.MeasurementPOJO;
import mongoOperations.MongoReader;
import org.envirocar.server.core.exception.GeometryConverterException;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
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


    public POJODatasetDump cloneIntoMemory() throws GeometryConverterException {

        /**
         * Dump is being populated
         */
        POJODatasetDump pojoDatasetDump = new POJODatasetDump();
        this.setExecutionStatus(ExecutionStatus.CLONING);
        List<MeasurementPOJO> measurementPOJOs = mongoReader.getAllMeasurements();
        for (MeasurementPOJO measurementPOJO : measurementPOJOs) {
            pojoDatasetDump.measurementPOJOList.add(measurementPOJO);
        }
        pojoDatasetDump.phenomenonPOJOList = mongoReader.getAllPhenomenon();
        pojoDatasetDump.trackPOJOList = mongoReader.getAllTracks();
        pojoDatasetDump.userPOJOList = mongoReader.getAllUsers();
        this.setExecutionStatus(ExecutionStatus.DATASETDUMPED);
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
