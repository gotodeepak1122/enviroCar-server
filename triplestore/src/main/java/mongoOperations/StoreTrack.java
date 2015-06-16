package mongoOperations;

import org.envirocar.server.core.entities.User;
import org.envirocar.server.mongo.entity.MongoTrack;

/**
 * @author deepaknair on 16/06/15 AD.
 */
public class StoreTrack extends MongoTrack {
    private User storeUser;

    public User getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(User storeUser) {
        this.storeUser = storeUser;
    }
}
