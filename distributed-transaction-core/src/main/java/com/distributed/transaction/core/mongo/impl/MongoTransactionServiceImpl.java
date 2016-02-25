package com.distributed.transaction.core.mongo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.distributed.transaction.api.TransactionState;
import com.distributed.transaction.core.mongo.pojo.TransactionMongoBean;
import com.distributed.transaction.core.mongo.repository.TransactionMongoBeanRepository;

/**
 * 
 * @author yubing
 *
 */

@Service("mongoTransactionService")
public class MongoTransactionServiceImpl extends AbstractMongoService<MongoTransactionService> implements MongoTransactionService {

	@Autowired
	private TransactionMongoBeanRepository transactionMongoBeanRepository;
	
	@Override
	public void saveTransactionMongoBean(
			TransactionMongoBean transactionMongoBean) {
		log.info("start to execute saveTransactionMongoBean,transactionMongoBean:{}",transactionMongoBean.toString());
		transactionMongoBeanRepository.save(transactionMongoBean);
		
	}

	@Override
	public void updateTransactionMongoBean(
			TransactionMongoBean transactionMongoBean) {
		log.info("start to execute updateTransactionMongoBean,transactionMongoBean:{}",transactionMongoBean.toString());
		
		Query query = new Query();
		query.addCriteria(new Criteria("transactionUUID").is(transactionMongoBean.getTransactionUUID()));
		Update update = new Update();
		update.set("transactionUUID",transactionMongoBean.getTransactionUUID());
		update.set("transactionState", transactionMongoBean.getTransactionState());
		update.set("transactionContent", transactionMongoBean.getTransactionContent());
		mongoTemplate.updateFirst(query, update, TransactionMongoBean.class);
		
		
	}

	@Override
	public TransactionMongoBean getTransactionMongoBean(String transactionUUID) {
		log.info("start to execute updateTransactionMongoBean,transactionUUID:{}",transactionUUID);
		Query query = new Query();  
        query.addCriteria(new Criteria("transactionUUID").is(transactionUUID));  
        return this.mongoTemplate.findOne(query, TransactionMongoBean.class);
	}

	@Override
	public void deleteTransactionMongoBean(TransactionMongoBean transactionMongoBean) {
		log.info("start to execute deleteTransaction,transactionUUID:{}",transactionMongoBean.getTransactionUUID());
		Query query = new Query();
		query.addCriteria(new Criteria("transactionUUID").is(transactionMongoBean.getTransactionUUID()));
		mongoTemplate.remove(query, TransactionMongoBean.class);
		
	}

	@Override
	public List<TransactionMongoBean> getRollbackErrorTransactionMongoBean() {
		log.info("start to execute getRollbackErrorTransactionMongoBean");
		Query query = new Query();
		query.addCriteria(new Criteria("transactionState").is(TransactionState.ROLLBACK_FAIL.getValue()));
		return mongoTemplate.find(query, TransactionMongoBean.class);
	}

}
