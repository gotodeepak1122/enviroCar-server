package mongoOperations;

import com.mongodb.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.envirocar.server.core.entities.Sensor;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.core.exception.GeometryConverterException;
import org.envirocar.server.mongo.entity.MongoMeasurement;
import org.envirocar.server.mongo.entity.MongoSensor;
import org.envirocar.server.mongo.entity.MongoUser;
import org.envirocar.server.mongo.util.GeoBSON;
import org.joda.time.DateTime;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author deepaknair on 14/06/15 AD.
 */

  /*TODO
   * rewrite code with morphia and giuce frameWork  -- find out appropriate measurement filter to use Morphia.get()
   *  Reuse String Db names
   */


/**
 * An entity that reads a collections store it in a measurement implementation POJO
 */
public class MongoReader {


    private static MongoClient mongoClient;
    private String DBName;
    private DBCollection collection;

    /**
     * Contructor that initilazes collection to be connected to
     *
     * @throws UnknownHostException
     */

    public MongoReader() throws UnknownHostException {
        mongoClient = new MongoClient();
        DB db = mongoClient.getDB("enviroCar");
        collection = db.getCollection("measurements");
    }

    public static void main(String[] args) throws UnknownHostException, GeometryConverterException {
        List<MongoMeasurement> mongoMeasurementList = new MongoReader().getAllMeasurements();
        for (MongoMeasurement mongoMeasurement : mongoMeasurementList) {
            System.out.println(mongoMeasurement.toString());
        }


    }

    public List<DBObject> getAllDBObjects() {
        DBCursor dbCursor = collection.find();
        List<DBObject> dbObjectList = new ArrayList<DBObject>();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            dbObjectList.add(dbObject);
        }
        return dbObjectList;
    }

    public List<MongoMeasurement> getAllMeasurements() throws GeometryConverterException {
        DBCursor dbCursor = collection.find();
        List<MongoMeasurement> measurementList = new ArrayList<MongoMeasurement>();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            measurementList.add(getMeasurementFromDbObject(dbObject));
        }
        return measurementList;
    }

    public MongoUser getUserFromDbObject(DBObject dbObject) {
        MongoUser mongoUser = new MongoUser();
        mongoUser.setToken((String) dbObject.get("token"));
        mongoUser.setAdmin((Boolean) dbObject.get("isAdmin"));
        mongoUser.setName((String) dbObject.get("_id"));
        mongoUser.setName((String) dbObject.get("mail"));
        System.out.println(mongoUser.toString());
        return mongoUser;
    }

    public MongoSensor getSensorFromDBObject(DBObject dbObject) {
        return null;
    }


    public MongoMeasurement getMeasurementFromDbObject(DBObject dbObject) throws GeometryConverterException {

        StoreMeasurement storeMeasurement = new StoreMeasurement();
        Geometry geometry = new GeoBSON(new GeometryFactory()).decode((BSONObject) dbObject.get("geometry"));
        DBRef userRefObject = (DBRef) dbObject.get("user");
        MongoUser mongoUser = getUserFromDbObject(userRefObject.fetch());
        storeMeasurement.setStoreUser(mongoUser);
        System.out.println(dbObject.get("sensor"));
        System.out.println("\n");
        DBRef trackRefObject = (DBRef) dbObject.get("track");
        System.out.println(trackRefObject.fetch().toString());
        System.out.println("\n");
        storeMeasurement.setId((ObjectId) dbObject.get("_id"));
        Date date = (Date) dbObject.get("time");
        storeMeasurement.setTime(new DateTime(date));
        storeMeasurement.setGeometry(geometry);
        storeMeasurement.setSensor((Sensor) dbObject.get("sensor"));
        storeMeasurement.setTrack((Track) dbObject.get("track"));
        return storeMeasurement;

    }


}
