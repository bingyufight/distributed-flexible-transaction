package com.distributed.transaction.core.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.distributed.transaction.core.mongo.pojo.TransactionMongoBean;

/**
 * 
 * @author yubing
 *
 */
public interface TransactionMongoBeanRepository extends MongoRepository<TransactionMongoBean, ObjectId> {

	TransactionMongoBean findByTransactionUUID(String transactionUUID);
}
