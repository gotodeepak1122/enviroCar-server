/*
 * Copyright (C) 2013 The enviroCar project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.envirocar.server.etl.dataSetDump.POJOEntities;

import com.google.common.collect.Sets;
import com.vividsolutions.jts.geom.Geometry;
import org.envirocar.server.core.entities.*;
import org.joda.time.DateTime;

import java.util.Set;

/**
 * @author deepaknair on 17/06/15 AD.
 *         A Measurement org.envirocar.server.etl.entity with only getters and setters
 *         Note: no db operations are implemented here , it's up to the extractor to implement extraction into this org.envirocar.server.etl.entity
 */


public class MeasurementPOJO implements Measurement {

    private Geometry geometry;
    private String identifier;
    private DateTime time;
    private MeasurementValuePOJO measurementValues;
    private UserPOJO user;
    private SensorPOJO sensor;
    private TrackPOJO track;
    private DateTime creationTime;
    private Set<MeasurementValuePOJO> values = Sets.newHashSet();
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
        return MeasurementValues.from(this.values).build();
    }

    @Override
    public void addValue(MeasurementValue value) {
        this.values.add((MeasurementValuePOJO) value);

    }

    @Override
    public void removeValue(MeasurementValue value) {
        this.values.remove(value);
    }


    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = (UserPOJO) user;
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
        this.sensor = (SensorPOJO) sensor;
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
        this.track = (TrackPOJO) track;
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
