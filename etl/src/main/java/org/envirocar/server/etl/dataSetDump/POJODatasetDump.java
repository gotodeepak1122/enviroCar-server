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
package org.envirocar.server.etl.dataSetDump;

import org.envirocar.server.etl.dataSetDump.POJOEntities.*;

import java.util.List;

/**
 * @author deepaknair on 17/06/15 AD.
 *         A dataset dump which loads data into POJO's
 *         Didnt think getter and setter would be required because it just functions as an intermittent data exchange format
 */


public class POJODatasetDump implements DatasetDump {

    public List<MeasurementPOJO> measurementPOJOList;
    public List<TrackPOJO> trackPOJOList;
    public List<SensorPOJO> sensorPOJOList;
    public List<BadgePOJO> badgePOJOList;
    public List<PhenomenonPOJO> phenomenonPOJOList;
    public List<UserPOJO> userPOJOList;
}
