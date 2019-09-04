package com.eureka.db;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.eureka.email.entities.Email;
import com.eureka.email.entities.MailInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DbContext {

	private static String dbname = "emailservicedb";

	public static String GetUserMailAddressWith(String userId) {

		String mailAdress = FindMailAddress(userId);
		return mailAdress;
	}

	public static String GetSystemMailAddress() {

		return FindMailAddress("1");
	}

	public static void saveEmail(Email email) {
		
		DBCollection collection = dbInstance().getCollection("emailcollection");
		BasicDBObject document = new BasicDBObject();

		document.put("userId", email.getUserId());
		document.put("mailContent", email.getMailContent());
		document.put("destinationAddress", email.getDestinationAddress());
		document.put("sourceAddress", email.getSourceAddress());
		document.put("mailHeader", email.getMailHeader());
 
		
		collection.insert(document);
	}

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

	public static void ShowAllMailInfo(String userId) {

		DBCollection table = dbInstance().getCollection("mailInfocollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", userId);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		System.out.println(FindMailAddress(userId));
	}

	public static String FindMailAddress(String userId) {
		DBCollection table = dbInstance().getCollection("mailInfocollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", userId);

		DBCursor cursor = table.find(searchQuery);
		while (cursor.hasNext()) {
			DBObject theObj = cursor.next();

			BasicDBObject mailInfo = (BasicDBObject) theObj;
			String mailAddress = mailInfo.getString("mailAddress");
			String id = mailInfo.getString("id");

			MailInfo mailInf = new MailInfo();
			mailInf.setMailAddress(mailAddress);
			mailInf.setId(id);
			mailInf.setUserId(userId);
			
			return mailAddress;

		}
		return "";
	}

	public static void SaveMailInfo(MailInfo mailInfo) throws Exception {

		DBCollection collection = dbInstance().getCollection("mailInfocollection");
		BasicDBObject document = new BasicDBObject();

		document.put("mailAddress", mailInfo.getMailAddress());
		document.put("userId", mailInfo.getUserId());

		collection.insert(document);
	}

	public static void Update(MailInfo mailInfo) {
		DBCollection collection = dbInstance().getCollection("mailInfocollection");

		BasicDBObject query = new BasicDBObject();
		query.put("id", mailInfo.getId());

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("mailAddress", mailInfo.getMailAddress());
		newDocument.put("userId", mailInfo.getUserId());

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		collection.update(query, updateObj);
	}

	public static void Delete(String id) {
		DBCollection collection = dbInstance().getCollection("mailInfocollection");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userId", id);

		collection.remove(searchQuery);
	}

}
