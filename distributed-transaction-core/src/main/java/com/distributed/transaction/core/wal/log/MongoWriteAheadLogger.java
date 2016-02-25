package com.distributed.transaction.core.wal.log;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.distributed.transaction.api.TransactionWriteAheadLog;
import com.distributed.transaction.api.TransactionWriteAheadLogType;
import com.distributed.transaction.common.util.AssertUtil;
import com.distributed.transaction.core.mongo.impl.MongoTransactionWriteAheadLogService;
import com.distributed.transaction.core.mongo.pojo.TransactionWriteAheadLogMongoBean;
import com.distributed.transaction.core.util.TransactionWriteAheadLogConvertMongoBeanHelper;


/**
 * 
 * @author yubing
 *
 *
 */

@Component
public class MongoWriteAheadLogger extends AbstractWriteAheadLogger<TransactionWriteAheadLog>{

	private static Logger logger = LoggerFactory.getLogger(MongoWriteAheadLogger.class);
	
	@Autowired
	MongoTransactionWriteAheadLogService mongoTransactionWriteAheadLogService;
	
	@Override
	public  boolean writeLogger(TransactionWriteAheadLog data) {
		logger.info("start to execute writeLogger,data:{}",data);
	    try{
	    	TransactionWriteAheadLogMongoBean transactionWriteAheadLogMongoBean = TransactionWriteAheadLogConvertMongoBeanHelper.transactionWriteAheadLogConvertToMongoBean(data);
	    	mongoTransactionWriteAheadLogService.saveTransactionWriteAheadLogMongoBean(transactionWriteAheadLogMongoBean);
	    }catch(Exception e){
	    	logger.error("writeLogger occur error",e);
	    	return false;
	    }
		return true;
	}

	
	@Override
	public List<TransactionWriteAheadLog> readDataFromLogger(
			String transactionUUID) {
		logger.info("start to execute readDateFromLoger,transactionUUID:{}",transactionUUID);
		
		List<TransactionWriteAheadLogMongoBean> mongoBeanList = mongoTransactionWriteAheadLogService.getTransactionWriteAheadLogMongoBean(transactionUUID);
		if(mongoBeanList != null && mongoBeanList.size() > 0){
			List<TransactionWriteAheadLog> returnList = new ArrayList<TransactionWriteAheadLog>();
			for(TransactionWriteAheadLogMongoBean transactionWriteAheadLogMongoBean : mongoBeanList){
				TransactionWriteAheadLog transactionWriteAheadLog = TransactionWriteAheadLogConvertMongoBeanHelper.transactionWriteAheadLogMongoBeanConvertToWriteAheadLog(transactionWriteAheadLogMongoBean);
				returnList.add(transactionWriteAheadLog);
			}
			
			return returnList;
		}
		return null;
	}
	


	public void setWriteAheadLoggerName(String loggerName) {
		AssertUtil.notNull(loggerName, "loggerName must not be null");
		this.writeAheadLoggerName = loggerName;
		
	}

	public String getWriteAheadLoggerName() {
	
		return this.writeAheadLoggerName;
	}

	@Override
	public void initConfig() {
		//TODO
	}

	@Override
	public void setLoggerStorePlace() {
		this.loggerStorePlace = "mongodb";
		
	}








	

}
