package entity.retrivedEntity.mongoRetrievedEntities;

import constants.DBNames;
import entity.retrivedEntity.RetrievedEntity;

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
