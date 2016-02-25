package com.distributed.transaction.core.mongo.impl;


import java.util.List;

import com.distributed.transaction.core.mongo.pojo.TransactionMongoBean;


/**
 * 
 * @author yubing
 *
 */

public interface MongoTransactionService {
	
	public void saveTransactionMongoBean(TransactionMongoBean transactionMongoBean);
	
	public void updateTransactionMongoBean(TransactionMongoBean transactionMongoBean);
	
	public TransactionMongoBean getTransactionMongoBean(String transactionUUID);
	
	public void deleteTransactionMongoBean(TransactionMongoBean transactionMongoBean);
	
	public List<TransactionMongoBean> getRollbackErrorTransactionMongoBean();
	

}
