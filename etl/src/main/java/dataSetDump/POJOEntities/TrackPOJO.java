package dataSetDump.POJOEntities;

import com.vividsolutions.jts.geom.Geometry;
import org.envirocar.server.core.entities.Sensor;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.core.entities.User;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author deepaknair on 17/06/15 AD.
 */
public class TrackPOJO implements Track {

    private String name;
    private String description;
    private String identifier;
    private Geometry boundingBox;
    private User user;
    private Sensor sensor;
    private double length;
    private DateTime begin;
    private DateTime end;
    private String touVersion;   //ask for a sample track with this attribute
    private String obdDevice;    // ask for a sample track with this attribute
    private String appVersion;   // ask for a sample track with this attribute
    private DateTime creationTime;
    private DateTime modificationTime;

    // specific to dumps , extracts all measurements of the track
    private List<MeasurementPOJO> measurementPOJOs;


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
        if (this.getName() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean hasDescription() {

        if (this.getDescription() == null) {
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
    public Geometry getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void setBoundingBox(Geometry boundingBox) {
        this.boundingBox = boundingBox;
    }

    @Override
    public boolean hasBoundingBox() {

        if (this.getBoundingBox() == null) {
            return false;
        } else
            return true;
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
    public double getLength() {
        return length;
    }

    @Override
    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public boolean hasLength() {

        if (this.getLength() == 0) {
            return false;
        } else
            return true;
    }

    @Override
    public DateTime getBegin() {
        return begin;
    }

    @Override
    public void setBegin(DateTime begin) {
        this.begin = begin;
    }

    @Override
    public boolean hasBegin() {

        if (this.getBegin() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public DateTime getEnd() {
        return end;
    }

    @Override
    public void setEnd(DateTime end) {
        this.end = end;
    }

    @Override
    public boolean hasEnd() {

        if (this.getEnd() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public String getTouVersion() {
        return touVersion;
    }

    @Override
    public void setTouVersion(String touVersion) {
        this.touVersion = touVersion;
    }


    @Override
    public String getObdDevice() {
        return obdDevice;
    }

    @Override
    public void setObdDevice(String obdDevice) {
        this.obdDevice = obdDevice;
    }


    @Override
    public String getAppVersion() {
        return appVersion;
    }

    @Override
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public boolean hasAppVersion() {
        if (this.getAppVersion() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public boolean hasObdDevice() {
        if (this.getObdDevice() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public boolean hasTouVersion() {
        if (this.getTouVersion() == null) {
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

    /**
     * this embeds all tracks in the list ,can be filled up by iterating through a measurement dump
     *
     * @return MeasurementPOJO List
     */

    public List<MeasurementPOJO> getMeasurementPOJOs() throws Exception {
        if (this.measurementPOJOs.size() == 0) {
            throw new Exception("Track " + this.getIdentifier() + "has no measurements");
        }
        return measurementPOJOs;
    }

    public void setMeasurementPOJOs(List<MeasurementPOJO> measurementPOJOs) {
        this.measurementPOJOs = measurementPOJOs;
    }

    public void addMeasurementPOJOs(MeasurementPOJO measurementPOJOs) {
        this.measurementPOJOs.add(measurementPOJOs);
    }
}