package com.eureka.db;

import java.util.List;
import java.util.Set;

import com.eureka.user.entities.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DbContext {

	private static String dbname = "userservicedb";




	private static String dbName = dbname;
	private static MongoClient mongoClient;

	private static MongoClient mongoClientInstance() {

		if (mongoClient == null) {
				mongoClient = new MongoClient("localhost", 27017);
			
		}

		return mongoClient;
	}

	private static DB _db;

	private static DB dbInstance() {

		if (_db == null) {
			_db = mongoClientInstance().getDB(dbName);
		}

		return _db;
	}

	public static void ShowAllCollections() {

		Set<String> tables = dbInstance().getCollectionNames();

		for (String coll : tables) {
			System.out.println("Collection name :" + coll);
		}
	}

	public static void ShowAllDB() {

		List<String> dbs = mongoClientInstance().getDatabaseNames();
		for (String db : dbs) {
			System.out.println(db);
		}
	}

 
	public static String FindUser(String eMail) {
		DBCollection table = dbInstance().getCollection("usercollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("eMail", eMail);

		DBCursor cursor = table.find(searchQuery);
		while (cursor.hasNext()) {
			DBObject theObj = cursor.next();

			BasicDBObject mailInfo = (BasicDBObject) theObj;
			String id = mailInfo.getString("userId");
 
			return id;
		}
		return "";
	}

	public static void SaveUser(User user) throws Exception {

		DBCollection collection = dbInstance().getCollection("usercollection");
		BasicDBObject document = new BasicDBObject();

		document.put("eMail", user.geteMail());
		document.put("userId", user.getUserId());

		collection.insert(document);
	}

	public static void deleteAll() {
		DBCollection collection = dbInstance().getCollection("usercollection");
 		BasicDBObject document = new BasicDBObject();
		collection.remove(document);
	}

 
 
}
