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
