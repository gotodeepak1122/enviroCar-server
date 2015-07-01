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

import org.envirocar.server.core.entities.Sensor;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * @author deepaknair on 17/06/15 AD.
 */


public class SensorPOJO implements Sensor {

    private String type;
    private Map<String, Object> properties = new HashMap<String, Object>();
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


