package dataSetDump.POJOEntities;

import org.envirocar.server.core.entities.Sensor;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public class SensorPOJO implements Sensor {

    private String type;
    private Map<String, Object> properties;
    private String identifier;
    private DateTime creationTime;
    private DateTime modificationTime;


    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean hasType() {
        if (this.getType() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public void addProperty(String key, Object val) {
        this.properties.put(key, val);
    }

    @Override
    public boolean hasProperties() {
        if (this.getProperties() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean hasIdentifier() {
        if (this.getType() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public DateTime getCreationTime() {
        return this.creationTime;
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


