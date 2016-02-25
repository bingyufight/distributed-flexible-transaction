package com.distributed.transaction.core.mongo.impl;

import java.util.List;

import com.distributed.transaction.core.mongo.pojo.TransactionWriteAheadLogMongoBean;


/**
 * 
 * @author yubing
 *
 */
public interface MongoTransactionWriteAheadLogService {
	
	public void saveTransactionWriteAheadLogMongoBean(TransactionWriteAheadLogMongoBean transactionWriteAheadLogMongoBean);
	
	public List<TransactionWriteAheadLogMongoBean> getTransactionWriteAheadLogMongoBean(String transactionUUID);

}
