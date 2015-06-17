package dataSetDump.POJOEntities;

import org.envirocar.server.core.entities.Phenomenon;
import org.joda.time.DateTime;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public class PhenomenonPOJO implements Phenomenon {

    private String name;
    private String unit;
    private DateTime creationTime;
    private DateTime modificationTime;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean hasName() {
        if (this.getCreationTime() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean hasUnit() {
        if (this.getUnit() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public DateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean hasCreationTime() {
        if (this.getCreationTime() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public DateTime getModificationTime() {
        return this.modificationTime;
    }

    @Override
    public boolean hasModificationTime() {
        if (this.getModificationTime() == null) {
            return false;
        } else
            return true;
    }


}
