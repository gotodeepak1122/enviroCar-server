package mongoOperations;

import com.mongodb.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.envirocar.server.core.exception.GeometryConverterException;
import org.envirocar.server.mongo.entity.MongoMeasurement;
import org.envirocar.server.mongo.entity.MongoSensor;
import org.envirocar.server.mongo.entity.MongoUser;
import org.envirocar.server.mongo.util.GeoBSON;
import org.joda.time.DateTime;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author deepaknair on 14/06/15 AD.
 */

  /*TODO
   * rewrite code with morphia and guice frameWork  -- find out appropriate measurement filter to use Morphia.get()
   *  Reuse String Db names
   *  TODO
   *  use Constants for db name
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
        return mongoUser;
    }

    public MongoSensor getSensorFromDBObject(DBObject dbObject) {


        MongoSensor mongoSensor = new MongoSensor();
        mongoSensor.setId((ObjectId) dbObject.get("_id"));
        mongoSensor.setType((String) dbObject.get("type"));
        BasicDBObject propertiesObject = (BasicDBObject) dbObject.get("properties");
        HashMap<String, Object> propertiesMap = new HashMap<String, Object>(propertiesObject.toMap());
        for (String key : propertiesMap.keySet()) {
            mongoSensor.addProperty(key, propertiesMap.get(key));
        }
        return mongoSensor;
    }

    public StoreTrack getTrackFromDBObject(DBObject dbObject) {

        // _id,DBREF user, sensor,name,description,begin , end , obddevice , length ,
        StoreTrack storeTrack = new StoreTrack();
        storeTrack.setId((ObjectId) dbObject.get("_id"));
        storeTrack.setName((String) dbObject.get("name"));
        storeTrack.setLength((Double) dbObject.get("length"));
        storeTrack.setObdDevice((String) dbObject.get("obdDevice"));
        DBRef dbRef = (DBRef) dbObject.get("user");
        storeTrack.setStoreUser((getUserFromDbObject(dbRef.fetch())));
        storeTrack.setSensor(getSensorFromDBObject((DBObject) dbObject.get("sensor")));
        storeTrack.setDescription((String) dbObject.get("description"));
        System.out.println(storeTrack.toString());
        return storeTrack;
    }



    public MongoMeasurement getMeasurementFromDbObject(DBObject dbObject) throws GeometryConverterException {

        StoreMeasurement storeMeasurement = new StoreMeasurement();
        DBRef userRefObject = (DBRef) dbObject.get("user");
        DBRef trackRefObject = (DBRef) dbObject.get("track");
        DBObject trackDBObject = trackRefObject.fetch();
        MongoUser mongoUser = getUserFromDbObject(userRefObject.fetch());
        Geometry geometry = new GeoBSON(new GeometryFactory()).decode((BSONObject) dbObject.get("geometry"));
        StoreTrack storeTrack = getTrackFromDBObject(trackDBObject);
        System.out.println(trackDBObject + "\n");
        MongoSensor sensor = getSensorFromDBObject((DBObject) dbObject.get("sensor"));
        storeMeasurement.setId((ObjectId) dbObject.get("_id"));
        Date date = (Date) dbObject.get("time");
        storeMeasurement.setTime(new DateTime(date));
        storeMeasurement.setGeometry(geometry);
        storeMeasurement.setSensor(sensor);
        storeMeasurement.setStoreTrack(storeTrack);
        return storeMeasurement;
    }


}
