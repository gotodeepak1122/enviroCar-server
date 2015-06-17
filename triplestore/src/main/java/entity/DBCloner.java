package entity;

import constants.ExecutionStatus;

import java.util.Date;

/**
 * @author deepaknair on 17/06/15 AD.
 */

/**
 * entities which clone data from Database style data sources
 */
public interface DBCloner extends Cloner {

    public Date getLastExecutionTime();

    public void runCloner();

    public ExecutionStatus getExecutionStatus();
}
