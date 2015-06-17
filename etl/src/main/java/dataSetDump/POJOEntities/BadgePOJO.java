package dataSetDump.POJOEntities;

import org.envirocar.server.core.entities.Badge;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * @author deepaknair on 17/06/15 AD.
 */
public class BadgePOJO implements Badge {

    private Map<String, String> description;
    private Map<String, String> DisplayName;
    private String name;
    private DateTime creationTime;
    private DateTime modificationTime;

    @Override
    public Map<String, String> getDescription() {
        return description;
    }

    @Override
    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    @Override
    public Map<String, String> getDisplayName() {
        return DisplayName;
    }

    @Override
    public void setDisplayName(Map<String, String> displayName) {
        DisplayName = displayName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public DateTime getModificationTime() {
        return modificationTime;
    }

    @Override
    public boolean hasCreationTime() {
        if (this.getCreationTime() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public boolean hasModificationTime() {
        if (this.getModificationTime() == null) {
            return false;
        } else
            return true;
    }


}
