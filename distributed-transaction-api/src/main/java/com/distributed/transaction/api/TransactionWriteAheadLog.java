package com.distributed.transaction.api;

import java.io.Serializable;


/**
 * 
 * @author yubing
 *
 */
public class TransactionWriteAheadLog implements Serializable {

	private static final long serialVersionUID = -7852895319590088874L;
	
	private final TransactionWriteAheadLogType transactionWriteAheadLogType;
	
	private final String transactionUUID;
	
	private final String ipHost;
	
	private final int port;
	
	private final String serviceName;
	
	private final TransactionInvocation logInvocation;
	
	private final long logCreateTime; 
	
	
	
	public TransactionWriteAheadLog(TransactionWriteAheadLogType transactionWriteAheadLogType,String ipHost,int port,String serviceName,TransactionInvocation logInvocation,String transactionUUID,long logCreateTime){
		this.transactionWriteAheadLogType = transactionWriteAheadLogType;
		this.ipHost = ipHost;
		this.port = port;
		this.serviceName = serviceName;
		this.logInvocation = logInvocation;
		this.transactionUUID = transactionUUID;
		this.logCreateTime = logCreateTime;
	}

	public TransactionWriteAheadLogType getTransactionWriteAheadLogType() {
		return transactionWriteAheadLogType;
	}

	public String getIpHost() {
		return ipHost;
	}

	public int getPort() {
		return port;
	}

	public String getServiceName() {
		return serviceName;
	}

	public TransactionInvocation getLogInvocation() {
		return logInvocation;
	}

	public long getLogCreateTime() {
		return logCreateTime;
	}

	public String getTransactionUUID() {
		return transactionUUID;
	}

	
	
	

}
