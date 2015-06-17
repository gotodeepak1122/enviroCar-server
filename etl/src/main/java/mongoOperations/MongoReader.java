package mongoOperations;

import com.mongodb.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import dataSetDump.POJOEntities.*;
import org.bson.BSONObject;
import org.envirocar.server.core.exception.GeometryConverterException;
import org.envirocar.server.mongo.entity.MongoMeasurementValue;
import org.envirocar.server.mongo.util.GeoBSON;
import org.joda.time.DateTime;

import java.net.UnknownHostException;
import java.util.*;


/**
 *   @author deepaknair on 14/06/15 AD.
 *   Mongo Utilities and processes that help the extractor
 *   TODO
 *   rewrite code with morphia and guice frameWork  -- find out appropriate measurement filter to use Morphia.get()
 *   Reuse String Db names
 *   TODO
 *   use Constants for db name
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

    public List<MeasurementPOJO> getAllMeasurements() throws GeometryConverterException {
        collection = db.getCollection("measurements");
        DBCursor dbCursor = collection.find();
        List<MeasurementPOJO> measurementList = new ArrayList<MeasurementPOJO>();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            measurementList.add(getMeasurementFromDbObject(dbObject));
        }
        return measurementList;
    }

    public List<TrackPOJO> getAllTracks() {
        collection = db.getCollection("tracks");
        List<TrackPOJO> storeTrackList = new ArrayList<TrackPOJO>();
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            storeTrackList.add(getTrackFromDBObject(dbObject));
        }
        return storeTrackList;
    }

    public List<SensorPOJO> getAllSensors() {
        collection = db.getCollection("sensors");
        List<SensorPOJO> sensorPOJOs = new ArrayList<SensorPOJO>();
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            sensorPOJOs.add(getSensorFromDBObject(dbObject));
        }
        return sensorPOJOs;
    }


    public List<UserPOJO> getAllUsers() {
        collection = db.getCollection("users");
        List<UserPOJO> UserList = new ArrayList<UserPOJO>();
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            UserList.add(getUserFromDbObject(dbObject));
        }
        return UserList;
    }


    public UserPOJO getUserFromDbObject(DBObject dbObject) {
        UserPOJO userPOJO = new UserPOJO();
        userPOJO.setToken((String) dbObject.get("token"));
        userPOJO.setAdmin((Boolean) dbObject.get("isAdmin"));
        userPOJO.setName((String) dbObject.get("_id"));
        userPOJO.setMail((String) dbObject.get("mail"));
        return userPOJO;
    }

    public SensorPOJO getSensorFromDBObject(DBObject dbObject) {


        SensorPOJO sensorPOJO = new SensorPOJO();
        sensorPOJO.setIdentifier((String) dbObject.get("_id"));
        sensorPOJO.setType((String) dbObject.get("type"));

        BasicDBObject propertiesObject = (BasicDBObject) dbObject.get("properties");

        HashMap<String, Object> propertiesMap = new HashMap<String, Object>(propertiesObject.toMap());
        for (String key : propertiesMap.keySet()) {
            sensorPOJO.addProperty(key, propertiesMap.get(key));
        }
        return sensorPOJO;
    }


    public TrackPOJO getTrackFromDBObject(DBObject dbObject) {

        // _id,DBREF user, sensor,name,description,begin , end , obddevice , length ,
        TrackPOJO storeTrack = new TrackPOJO();
        storeTrack.setIdentifier((String) dbObject.get("_id"));
        storeTrack.setName((String) dbObject.get("name"));
        storeTrack.setLength((Double) dbObject.get("length"));
        storeTrack.setObdDevice((String) dbObject.get("obdDevice"));
        DBRef dbRef = (DBRef) dbObject.get("user");
        storeTrack.setUser((getUserFromDbObject(dbRef.fetch())));
        storeTrack.setSensor(getSensorFromDBObject((DBObject) dbObject.get("sensor")));
        storeTrack.setDescription((String) dbObject.get("description"));
        return storeTrack;
    }


    public MeasurementValuePOJO getMeasurementValueFromDBObject(BasicDBObject basicDBObject) {

        MeasurementValuePOJO measurementValuePOJO = new MeasurementValuePOJO();
        Map<String, Object> hashMap = basicDBObject.toMap();
        measurementValuePOJO.setValue(hashMap.get("value"));
        measurementValuePOJO.setPhenomenon(getPhenomenonFromDBObject((BasicDBObject) hashMap.get("phen")));
        System.out.println(measurementValuePOJO.getPhenomenon().getUnit());
        return measurementValuePOJO;

    }


    public PhenomenonPOJO getPhenomenonFromDBObject(BasicDBObject basicDBObject) {
        PhenomenonPOJO phenomenonPOJO = new PhenomenonPOJO();
        phenomenonPOJO.setName((String) basicDBObject.toMap().get("_id"));
        phenomenonPOJO.setUnit((String) basicDBObject.toMap().get("unit"));
        return phenomenonPOJO;
    }


    public MeasurementPOJO getMeasurementFromDbObject(DBObject dbObject) throws GeometryConverterException {

        MeasurementPOJO measurementPOJO = new MeasurementPOJO();
        DBRef userRefObject = (DBRef) dbObject.get("user");
        DBRef trackRefObject = (DBRef) dbObject.get("track");
        DBObject trackDBObject = trackRefObject.fetch();
        UserPOJO userPOJO = getUserFromDbObject(userRefObject.fetch());
        Geometry geometry = new GeoBSON(new GeometryFactory()).decode((BSONObject) dbObject.get("geometry"));
        TrackPOJO trackPOJO = getTrackFromDBObject(trackDBObject);
        System.out.println(trackDBObject + "\n");
        SensorPOJO sensor = getSensorFromDBObject((DBObject) dbObject.get("sensor"));
        measurementPOJO.setIdentifier((String) dbObject.get("_id"));
        BasicDBList basicDBList = (BasicDBList) dbObject.get("phenomenons");
        MongoMeasurementValue mongoMeasurementValue = new MongoMeasurementValue();
        Set<MeasurementValuePOJO> measurementValues = new HashSet<MeasurementValuePOJO>();
        for (Object object : basicDBList) {
            measurementPOJO.addValue(getMeasurementValueFromDBObject((BasicDBObject) object));

        }
        Date date = (Date) dbObject.get("time");
        measurementPOJO.setTime(new DateTime(date));
        measurementPOJO.setGeometry(geometry);
        measurementPOJO.setSensor(sensor);
        measurementPOJO.setTrack(trackPOJO);
        return measurementPOJO;
    }


}
