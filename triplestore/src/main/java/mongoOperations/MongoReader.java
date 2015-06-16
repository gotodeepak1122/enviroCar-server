package mongoOperations;

import com.mongodb.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.envirocar.server.core.exception.GeometryConverterException;
import org.envirocar.server.mongo.entity.*;
import org.envirocar.server.mongo.util.GeoBSON;
import org.joda.time.DateTime;

import java.net.UnknownHostException;
import java.util.*;

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
    private DB db;
    /**
     * Contructor that initilazes collection to be connected to
     *
     * @throws UnknownHostException
     */

    public MongoReader() throws UnknownHostException {
        mongoClient = new MongoClient();
        db = mongoClient.getDB("enviroCar");
    }


    public List<DBObject> getAllDBObjectsFromCollections(String collectionName) {
        collection = db.getCollection(collectionName);
        DBCursor dbCursor = collection.find();
        List<DBObject> dbObjectList = new ArrayList<DBObject>();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            dbObjectList.add(dbObject);
        }
        return dbObjectList;
    }

    public List<MongoMeasurement> getAllMeasurements() throws GeometryConverterException {
        collection = db.getCollection("measuremnts");
        DBCursor dbCursor = collection.find();
        List<MongoMeasurement> measurementList = new ArrayList<MongoMeasurement>();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            measurementList.add(getMeasurementFromDbObject(dbObject));
        }
        return measurementList;
    }

    public List<StoreTrack> getAllTracks() {
        collection = db.getCollection("tracks");
        List<StoreTrack> storeTrackList = new ArrayList<StoreTrack>();
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            storeTrackList.add(getTrackFromDBObject(dbObject));
        }
        return storeTrackList;
    }

    public List<MongoSensor> getAllSensors() {
        collection = db.getCollection("sensors");
        List<MongoSensor> mongoSensorArrayList = new ArrayList<MongoSensor>();
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            mongoSensorArrayList.add(getSensorFromDBObject(dbObject));
        }
        return mongoSensorArrayList;
    }


    public List<MongoUser> getAllUsers() {
        collection = db.getCollection("users");
        List<MongoUser> UserList = new ArrayList<MongoUser>();
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            UserList.add(getUserFromDbObject(dbObject));
        }
        return UserList;
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
        return storeTrack;
    }

    public MongoMeasurementValue getMeasurementValueFromDBObject(BasicDBObject basicDBObject) {
        int count = 0;
        System.out.println("inside function");
        MongoMeasurementValue mongoMeasurementValue = new MongoMeasurementValue();
        Map<String, Object> hashMap = basicDBObject.toMap();
        mongoMeasurementValue.setValue(hashMap.get("value"));
        mongoMeasurementValue.setPhenomenon(getPhenomenonFromDBObject((BasicDBObject) hashMap.get("phen")));
        System.out.println(mongoMeasurementValue.getPhenomenon().getUnit());
        System.out.println("outside function");
        return mongoMeasurementValue;


    }

    public MongoPhenomenon getPhenomenonFromDBObject(BasicDBObject basicDBObject) {
        MongoPhenomenon mongoPhenomenon = new MongoPhenomenon();
        mongoPhenomenon.setName((String) basicDBObject.toMap().get("_id"));
        mongoPhenomenon.setUnit((String) basicDBObject.toMap().get("unit"));
        return mongoPhenomenon;
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
        BasicDBList basicDBList = (BasicDBList) dbObject.get("phenomenons");
        MongoMeasurementValue mongoMeasurementValue = new MongoMeasurementValue();
        Set<MongoMeasurementValue> measurementValues = new HashSet<MongoMeasurementValue>();
        for (Object object : basicDBList) {
            measurementValues.add(getMeasurementValueFromDBObject((BasicDBObject) object));

        }
        Date date = (Date) dbObject.get("time");
        storeMeasurement.setTime(new DateTime(date));
        storeMeasurement.setGeometry(geometry);
        storeMeasurement.setSensor(sensor);
        storeMeasurement.setStoreTrack(storeTrack);
        storeMeasurement.setStoreMeasurementValues(measurementValues);
        return storeMeasurement;
    }


}
