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

import org.envirocar.server.core.activities.Activity;
import org.envirocar.server.core.activities.ActivityType;
import org.joda.time.DateTime;

/**
 * @author deepaknair on 12/08/15.
 */
public class ActivityPOJO implements Activity {

    private String identifier;
    private UserPOJO user;
    private ActivityType type;
    private DateTime time;

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public UserPOJO getUser() {
        return this.user;
    }

    public void setUser(UserPOJO user) {
        this.user = user;
    }

    @Override
    public boolean hasUser() {
        if (this.getUser() != null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public ActivityType getType() {
        return this.type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    @Override
    public boolean hasType() {
        if (this.getType() == null) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public DateTime getTime() {
        return this.time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public boolean hasTime() {
        if (this.getTime() == null) {
            return false;
        } else {
            return true;
        }
    }
}
