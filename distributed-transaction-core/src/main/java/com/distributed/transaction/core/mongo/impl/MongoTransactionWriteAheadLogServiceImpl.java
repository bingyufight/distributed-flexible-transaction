package com.distributed.transaction.core.mongo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.distributed.transaction.core.mongo.pojo.TransactionWriteAheadLogMongoBean;
import com.distributed.transaction.core.mongo.repository.TransactionWriteAheadLogMongoBeanRepository;


/**
 * 
 * @author yubing
 *
 */

@Service("mongoTransactionWriteAheadLogService")
public class MongoTransactionWriteAheadLogServiceImpl extends
		AbstractMongoService<MongoTransactionWriteAheadLogService> implements MongoTransactionWriteAheadLogService {

	@Autowired
	private TransactionWriteAheadLogMongoBeanRepository transactionWriteAheadLogMongoBeanRepository;
	
	
	@Override
	public void saveTransactionWriteAheadLogMongoBean(
			TransactionWriteAheadLogMongoBean transactionWriteAheadLogMongoBean) {
		transactionWriteAheadLogMongoBeanRepository.save(transactionWriteAheadLogMongoBean);
	}

	@Override
	public List<TransactionWriteAheadLogMongoBean> getTransactionWriteAheadLogMongoBean(
			String transactionUUID) {
		Query query = new Query();
		query.addCriteria(new Criteria("transactionUUID").is(transactionUUID));
		return mongoTemplate.find(query, TransactionWriteAheadLogMongoBean.class);
	}

}
