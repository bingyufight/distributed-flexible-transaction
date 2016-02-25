package com.distributed.transaction.core.wal.log;


/**
 * 
 * @author yubing
 *
 */
public abstract class AbstractWriteAheadLogger<T> implements IWriteAheadLogger<T> {
	
	
	protected String loggerStorePlace;
	
	protected String writeAheadLoggerName;
	
	public abstract void initConfig();
	
	public abstract void setLoggerStorePlace();

	public String getLoggerStorePlace() {
		return loggerStorePlace;
	}
	
}
