package com.distributed.transaction.core.wal.log;

import java.util.List;


/**
 * 
 * @author yubing
 *
 */
public interface IWriteAheadLogger<T> {

	 boolean  writeLogger(T data);
	 
	 List<T> readDataFromLogger(String transactionUUID);
	 
	 void setWriteAheadLoggerName(String loggerName);
	 
	 String getWriteAheadLoggerName();
	
}
