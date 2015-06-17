package entity;

import constants.ExecutionStatus;
import mongoOperations.MongoReader;

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


    @Override
    public Date getLastExecutionTime() {
        return this.LastExecutionTime;
    }

    @Override
    public void runCloner() {

    }

    @Override
    public ExecutionStatus getExecutionStatus() {
        return null;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

}
