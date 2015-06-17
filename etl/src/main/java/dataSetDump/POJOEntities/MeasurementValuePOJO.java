package dataSetDump.POJOEntities;

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
