package dataSetDump.POJOEntities;

import com.vividsolutions.jts.geom.Geometry;
import org.envirocar.server.core.entities.*;
import org.joda.time.DateTime;

/**
 * @author deepaknair on 17/06/15 AD.
 *         A Measurement entity with only getters and setters
 *         Note: no db operations are implemented here , it's up to the extractor to implement extraction into this entity
 */


public class MeasurementPOJO implements Measurement {
    @Override
    public Geometry getGeometry() {
        return null;
    }

    @Override
    public void setGeometry(Geometry geometry) {

    }

    @Override
    public boolean hasGeometry() {
        return false;
    }

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public void setIdentifier(String identifier) {

    }

    @Override
    public boolean hasIdentifier() {
        return false;
    }

    @Override
    public DateTime getTime() {
        return null;
    }

    @Override
    public void setTime(DateTime time) {

    }

    @Override
    public boolean hasTime() {
        return false;
    }

    @Override
    public MeasurementValues getValues() {
        return null;
    }

    @Override
    public void addValue(MeasurementValue value) {

    }

    @Override
    public void removeValue(MeasurementValue value) {

    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public boolean hasUser() {
        return false;
    }

    @Override
    public Sensor getSensor() {
        return null;
    }

    @Override
    public void setSensor(Sensor sensor) {

    }

    @Override
    public boolean hasSensor() {
        return false;
    }

    @Override
    public Track getTrack() {
        return null;
    }

    @Override
    public void setTrack(Track track) {

    }

    @Override
    public boolean hasTrack() {
        return false;
    }

    @Override
    public DateTime getCreationTime() {
        return null;
    }

    @Override
    public boolean hasCreationTime() {
        return false;
    }

    @Override
    public DateTime getModificationTime() {
        return null;
    }

    @Override
    public boolean hasModificationTime() {
        return false;
    }

    @Override
    public int compareTo(Measurement o) {
        return 0;
    }
}
