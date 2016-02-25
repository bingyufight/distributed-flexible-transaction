package com.distributed.transaction.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.distributed.transaction.api.Transaction;
import com.distributed.transaction.common.util.AssertUtil;
import com.distributed.transaction.core.mongo.impl.MongoTransactionService;
import com.distributed.transaction.core.mongo.pojo.TransactionMongoBean;
import com.distributed.transaction.core.util.TransactionConvertMongoBeanHelper;

/**
 * 
 * @author yubing
 *
 */

@Service("mongoDistributedTransactionRepository")
public class MongoDistributedTransactionRepository implements
		DistributedTransactionRepository {

	
	private static Logger logger = LoggerFactory.getLogger(MongoDistributedTransactionRepository.class);
	
	@Autowired
	private MongoTransactionService mongoTransactionService; 
	
	@Override
	public void saveTransaction(Transaction transaction) {
		AssertUtil.notNull(transaction);
		logger.info("start to execute saveTransaction,transactionUUID:{}",transaction.getTransactionGlobalId().getGlobalTransactionUUID());
		TransactionMongoBean transactionMongoBean = TransactionConvertMongoBeanHelper.transactionConvertToMongoBean(transaction);
		mongoTransactionService.saveTransactionMongoBean(transactionMongoBean);
	}

	@Override
	public void updateTransaction(Transaction transaction) {
		AssertUtil.notNull(transaction);
		logger.info("start to execute updateTransaction,transactionUUID:{}",transaction.getTransactionGlobalId().getGlobalTransactionUUID());
		TransactionMongoBean transactionMongoBean = TransactionConvertMongoBeanHelper.transactionConvertToMongoBean(transaction);
		mongoTransactionService.updateTransactionMongoBean(transactionMongoBean);

	}

	@Override
	public Transaction getTransaction(String transactionUUID) {
		AssertUtil.notNull(transactionUUID);
		TransactionMongoBean transactionMongoBean = mongoTransactionService.getTransactionMongoBean(transactionUUID);
		if(transactionMongoBean != null){
			Transaction transaction = TransactionConvertMongoBeanHelper.transactionMongoBeanConvertToTransaction(transactionMongoBean);
			return transaction;
		}
		return null;
	}

	@Override
	public void deleteTransaction(Transaction transaction) {
		AssertUtil.notNull(transaction);
		TransactionMongoBean transactionMongoBean = TransactionConvertMongoBeanHelper.transactionConvertToMongoBean(transaction);
		mongoTransactionService.deleteTransactionMongoBean(transactionMongoBean);
		
	}

	@Override
	public List<Transaction> getRollbackErrorTransaction() {
	    
		List<TransactionMongoBean> transactionMongoBeanList = mongoTransactionService.getRollbackErrorTransactionMongoBean();
		if(transactionMongoBeanList != null && transactionMongoBeanList.size() > 0){
			List<Transaction> transactionList = new ArrayList<Transaction>();
			for(TransactionMongoBean transactionMongoBean : transactionMongoBeanList){
				if(transactionMongoBean != null){
					Transaction transaction = TransactionConvertMongoBeanHelper.transactionMongoBeanConvertToTransaction(transactionMongoBean);
					transactionList.add(transaction);
				}
			}
			return transactionList;
		}
		return null;
	}

}
