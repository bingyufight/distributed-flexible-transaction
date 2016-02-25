package com.distributed.transaction.core.wal.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.distributed.transaction.api.TransactionWriteAheadLog;

/**
 * 
 * @author yubing
 *
 */

public class AsyncMongoTransactionWriteAheadLogTask implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(AsyncMongoTransactionWriteAheadLogTask.class);

	private IWriteAheadLogger<TransactionWriteAheadLog> writeAheadLogger;
	
	private TransactionWriteAheadLog transactionWriteAheadLog;
	
	public AsyncMongoTransactionWriteAheadLogTask(IWriteAheadLogger<TransactionWriteAheadLog> writeAheadLogger,TransactionWriteAheadLog transactionWriteAheadLog){
		this.writeAheadLogger = writeAheadLogger;
		this.transactionWriteAheadLog = transactionWriteAheadLog;
		
	}
	
	@Override
	public void run() {
	    long executeStartTime = System.currentTimeMillis();
	    logger.info("AsyncMongoTransactionWriteAheadLogTask run at the time:{}",executeStartTime);
	    
		writeAheadLogger.writeLogger(transactionWriteAheadLog);
		
		long executeEndTime = System.currentTimeMillis();
		long lost = executeEndTime-executeStartTime;
	    logger.info("AsyncMongoTransactionWriteAheadLogTask total run:{} ms",lost );

	}

}
