package com.distributed.transaction.core.mongo.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.DatastoreImpl;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * 
 * @author yubing
 *
 */
public class MongoDatastoreTemplate{

	private Datastore ds;
	
	private Morphia morphia;
	
	private DB db;
	
	public MongoDatastoreTemplate(Mongo mongo,
						  Morphia morphia,
						  String dbName,
						  String username, 
						  String password) {
		this.morphia = morphia;
		
		//this.morphia.map(AccessToken.class);
		
		this.db = mongo.getDB(dbName);
		
		//db.authenticate(username, password.toCharArray());
		
		ds = new DatastoreImpl(this.morphia, mongo, dbName, username, password.toCharArray());
	//	ds.ensureIndexes();
		
		/*
		 * @Embedded Index
		 */	
	}
	
	public Datastore getDs(){
		return this.ds;
	}
	
	public Morphia getMorphia(){
		return this.morphia;
	}
	
	public DB getDb(){
		return this.db;
	}
	
	public DBCollection getDBCollection(String cName){
		return db.getCollection(cName);
	}
}
