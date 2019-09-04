package com.eureka.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;

import com.eureka.note.entities.Note;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DbContext {

	private static String dbName = "noteservicedb";
	private static MongoClient mongoClient;

	private static MongoClient mongoClientInstance() {

		if (mongoClient == null) {
			   mongoClient = new MongoClient("localhost", 27017);
			 
		}

		return mongoClient;
	}

	private static DB db;

	private static DB dbInstance() {

		if (db == null) {
			db = mongoClientInstance().getDB(dbName);
		}

		return db;
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

	public static void ShowAllNotes(int userId) {

		DBCollection table = dbInstance().getCollection("notecollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", userId);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}

	}

	public static Note GetNote(String id) throws Exception {

		DBCollection table = dbInstance().getCollection("notecollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", new ObjectId(id));

		DBObject theObj = table.findOne(searchQuery);

		BasicDBObject dbObject = (BasicDBObject) theObj;

		String noteHeader = (String) dbObject.get("noteHeader");
		String noteDetail = (String) dbObject.get("noteDetail");
		String userId = (String) dbObject.get("userId");
		Note note = new Note(userId, noteHeader, noteDetail);
		
		Thread.sleep(10000);

		return note;

	}

	public static List<Note> GetUserAllNotes(String userId) throws Exception {

		List<Note> notes = new ArrayList<Note>();
		DBCollection table = dbInstance().getCollection("notecollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", userId);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			DBObject theObj = cursor.next();
			BasicDBObject dbObject = (BasicDBObject) theObj;
			ObjectId id = (ObjectId) dbObject.get("id");
			System.out.println(id);
			String noteHeader = (String) dbObject.get("noteHeader");
			String noteDetail = (String) dbObject.get("noteDetail");
			Note note = new Note(id.toString() ,userId, noteHeader, noteDetail);
			notes.add(note);
		}
		Thread.sleep(10000);

		return notes;

	}

	public static Note Save(Note note) throws Exception {

		DBCollection collection = dbInstance().getCollection("notecollection");
		BasicDBObject document = new BasicDBObject();
		ObjectId id = ObjectId.get();
		document.put("id",id);
		document.put("noteDetail", note.getNoteDetail());
		document.put("noteHeader", note.getNoteHeader());
		document.put("userId", note.getUserId());

		collection.insert(document);
		note.setId(id.toString());
		Thread.sleep(10000);
		return note;
	}

	public static void Update(Note note) throws Exception {
		
		DBCollection collection = dbInstance().getCollection("notecollection");

		BasicDBObject query = new BasicDBObject();
		query.put("id",  new ObjectId(note.getId()));

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("noteDetail", note.getNoteDetail());
		newDocument.put("noteHeader", note.getNoteHeader());

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		collection.update(query, updateObj);
		Thread.sleep(10000);

	}

	public static void Delete(String id) {
		DBCollection collection = dbInstance().getCollection("notecollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", new ObjectId(id));

		collection.remove(searchQuery);
	}

	public static void DeleteAll(String userId) {
		DBCollection collection = dbInstance().getCollection("notecollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", userId);

		collection.remove(searchQuery);
	}
}
