package com.distributed.transaction.core.mongo.impl;

import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.distributed.transaction.core.mongo.dao.MongoDatastoreTemplate;

/**
 * 
 * @author yubing
 *
 */
public abstract class AbstractMongoService<T> {

	@Autowired protected MongoTemplate mongoTemplate;
	
	@Autowired protected  MongoDatastoreTemplate  mongoDatastoreTemplate;
	
	@SuppressWarnings("unchecked")
	final protected Logger log = LoggerFactory.getLogger(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));


}
