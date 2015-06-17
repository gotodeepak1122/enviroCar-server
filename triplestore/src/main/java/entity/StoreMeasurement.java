package entity;

import mongoOperations.StoreTrack;
import org.envirocar.server.core.entities.User;
import org.envirocar.server.mongo.entity.MongoMeasurement;
import org.envirocar.server.mongo.entity.MongoMeasurementValue;
import org.envirocar.server.mongo.entity.MongoUser;

import java.util.Set;

/**
 * @author deepaknair on 16/06/15 AD.
 */
public class StoreMeasurement extends MongoMeasurement {
    private MongoUser storeUser;
    private StoreTrack storeTrack;
    private Set<MongoMeasurementValue> storeMeasurementValues;

    public MongoUser getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(User user) {
        this.storeUser = (MongoUser) user;
    }

    public StoreTrack getStoreTrack() {
        return storeTrack;
    }

    public void setStoreTrack(StoreTrack storeTrack) {
        this.storeTrack = storeTrack;
    }

    public Set<MongoMeasurementValue> getStoreMeasurementValues() {
        return storeMeasurementValues;
    }

    public void setStoreMeasurementValues(Set<MongoMeasurementValue> storeMeasurementValues) {
        this.storeMeasurementValues = storeMeasurementValues;
    }
}
