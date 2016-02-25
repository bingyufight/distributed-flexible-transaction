package com.distributed.transaction.core.wal.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.distributed.transaction.api.Participant;
import com.distributed.transaction.api.TransactionInvocation;
import com.distributed.transaction.api.TransactionWriteAheadLog;
import com.distributed.transaction.api.TransactionWriteAheadLogType;
import com.distributed.transaction.common.threadpool.CustomAsyncThreadPool;
import com.distributed.transaction.common.util.AssertUtil;

/**
 * 
 * @author yubing
 *
 */
@Component
public class AsyncMongoTransactionWriteAheadLogComponent {
	
	private static Logger logger = LoggerFactory.getLogger(AsyncMongoTransactionWriteAheadLogComponent.class);
	
	@Autowired
	private MongoWriteAheadLogger mongoWriteAheadLogger;
	
	public void writeAheadLog(Participant participant,TransactionWriteAheadLogType transactionWriteAheadLogType){
		AssertUtil.notNull(participant);
		AssertUtil.notNull(transactionWriteAheadLogType);
		
		logger.info("writeAheadLog,transactionUUID:{},ipHost:{},port:{},serviceName:{}",participant.getTransactionUUID(),participant.getParticipantIpHost(),participant.getParticipantPort(),participant.getParticipantServiceName());
		
	    TransactionInvocation transactionInvocation = null;
	    if(transactionWriteAheadLogType == TransactionWriteAheadLogType.LOG_PARTICIPANT_ENROLL_BEFORE_COMMIT){
	    	transactionInvocation = participant.getCommitTransactionInvcoation();
	    } else {
	    	transactionInvocation = participant.getRollbackTransactionInvocation();
	    }
		
		TransactionWriteAheadLog transactionWriteAheadLog = new TransactionWriteAheadLog(transactionWriteAheadLogType,participant.getParticipantIpHost(),participant.getParticipantPort(),
				participant.getParticipantServiceName(),transactionInvocation,participant.getTransactionUUID(),System.currentTimeMillis());
		
		AsyncMongoTransactionWriteAheadLogTask asyncMongoTransactionWriteAheadLogTask = new AsyncMongoTransactionWriteAheadLogTask(mongoWriteAheadLogger,transactionWriteAheadLog);
		
		CustomAsyncThreadPool.getInstance().submitTask(asyncMongoTransactionWriteAheadLogTask);
		
		
	}
	
	
	
	

}
