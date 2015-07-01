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
package org.envirocar.server.etl.entity.retrivedEntity.mongoRetrievedEntities;

import org.envirocar.server.etl.constants.DBNames;
import org.envirocar.server.etl.entity.retrivedEntity.RetrievedEntity;

import java.util.Map;

/**
 * @author deepaknair on 17/06/15 AD.
 */
public abstract class BaseMongoRetrievedEntity implements RetrievedEntity {


    /**
     * Used to show the relation between retrieved Coloums names in Mongo and the respective stored attributes names
     */
    public Map<String, String> TypeConversionMap;

    @Override
    public String getRetrievedFrom() {
        return DBNames.MONGO_DB;
    }

    public void displayTypeConversionMap() {
        System.out.println(this.getClass() + "   Relationship");
        for (String Key : TypeConversionMap.keySet()) {
            System.out.println(Key + "-->" + TypeConversionMap.get(Key));
        }
    }


}
