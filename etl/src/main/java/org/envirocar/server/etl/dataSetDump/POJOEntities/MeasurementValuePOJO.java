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

import org.envirocar.server.core.entities.MeasurementValue;
import org.envirocar.server.core.entities.Phenomenon;

/**
 * @author deepaknair on 17/06/15 AD.
 */
public class MeasurementValuePOJO implements MeasurementValue {

    Double value;
    PhenomenonPOJO phenomenonPOJO;


    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (Double) value;
    }

    @Override
    public boolean hasValue() {
        return this.getValue() != null;
    }

    @Override
    public Phenomenon getPhenomenon() {
        return this.phenomenonPOJO;
    }

    @Override
    public void setPhenomenon(Phenomenon phenomenon) {
        this.phenomenonPOJO = (PhenomenonPOJO) phenomenon;

    }

    @Override
    public boolean hasPhenomenon() {
        return this.getPhenomenon() != null;
    }
}
