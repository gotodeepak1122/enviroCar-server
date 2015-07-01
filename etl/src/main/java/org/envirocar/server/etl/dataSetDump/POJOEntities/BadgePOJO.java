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
