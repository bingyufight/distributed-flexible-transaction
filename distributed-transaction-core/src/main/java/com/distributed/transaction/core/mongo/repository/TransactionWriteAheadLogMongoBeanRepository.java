package com.distributed.transaction.core.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.distributed.transaction.core.mongo.pojo.TransactionWriteAheadLogMongoBean;


/**
 * 
 * @author yubing
 *
 */
public interface TransactionWriteAheadLogMongoBeanRepository extends
		MongoRepository<TransactionWriteAheadLogMongoBean, ObjectId> {

}
