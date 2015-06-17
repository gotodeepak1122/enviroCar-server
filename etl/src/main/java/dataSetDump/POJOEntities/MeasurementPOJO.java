package dataSetDump.POJOEntities;

import com.google.common.collect.Sets;
import com.vividsolutions.jts.geom.Geometry;
import org.envirocar.server.core.entities.*;
import org.joda.time.DateTime;

import java.util.Set;

/**
 * @author deepaknair on 17/06/15 AD.
 *         A Measurement entity with only getters and setters
 *         Note: no db operations are implemented here , it's up to the extractor to implement extraction into this entity
 */


public class MeasurementPOJO implements Measurement {

    private Geometry geometry;
    private String identifier;
    private DateTime time;
    private MeasurementValue measurementValues;
    private UserPOJO user;
    private SensorPOJO sensor;
    private TrackPOJO track;
    private DateTime creationTime;
    private Set<MeasurementValuePOJO values = Sets.newHashSet();
    private DateTime modificationTime;


    @Override
    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public boolean hasGeometry() {
        if (this.getGeometry() == null) {
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
        if (this.getIdentifier() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public DateTime getTime() {
        return time;
    }

    @Override
    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public boolean hasTime() {
        if (this.getTime() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public MeasurementValues getValues() {
        return null;
    }

    @Override
    public void addValue(MeasurementValue value) {
        this.values.ad

    }

    @Override
    public void removeValue(MeasurementValue value) {

    }


    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean hasUser() {
        if (this.getUser() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public Sensor getSensor() {
        return sensor;
    }

    @Override
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean hasSensor() {
        if (this.getSensor() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public Track getTrack() {
        return track;
    }

    @Override
    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public boolean hasTrack() {
        if (this.getTrack() == null) {
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


    @Override
    public int compareTo(Measurement o) {
        return 0;
    }


}
