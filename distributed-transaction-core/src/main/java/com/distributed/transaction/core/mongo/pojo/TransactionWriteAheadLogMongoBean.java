package com.distributed.transaction.core.mongo.pojo;

import java.util.Arrays;

import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 
 * @author yubing
 *
 */
@Document(collection="transaction_write_ahead_log_mongo_bean")
public class TransactionWriteAheadLogMongoBean {
	
	private int transactionWriteAheadLogTypeValue;
	
	private String transactionWriteAheadLogTypeDesc;
	
	private String transactionUUID;
	
	private String ipHost;
	
	private int port;
	
	private String serviceName;
	
	private byte[] logInvocation;
	
	private long logCreateTime;

	public int getTransactionWriteAheadLogTypeValue() {
		return transactionWriteAheadLogTypeValue;
	}

	public void setTransactionWriteAheadLogTypeValue(
			int transactionWriteAheadLogTypeValue) {
		this.transactionWriteAheadLogTypeValue = transactionWriteAheadLogTypeValue;
	}

	public String getTransactionWriteAheadLogTypeDesc() {
		return transactionWriteAheadLogTypeDesc;
	}

	public void setTransactionWriteAheadLogTypeDesc(
			String transactionWriteAheadLogTypeDesc) {
		this.transactionWriteAheadLogTypeDesc = transactionWriteAheadLogTypeDesc;
	}

	public String getTransactionUUID() {
		return transactionUUID;
	}

	public void setTransactionUUID(String transactionUUID) {
		this.transactionUUID = transactionUUID;
	}

	public String getIpHost() {
		return ipHost;
	}

	public void setIpHost(String ipHost) {
		this.ipHost = ipHost;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public byte[] getLogInvocation() {
		return logInvocation;
	}

	public void setLogInvocation(byte[] logInvocation) {
		this.logInvocation = logInvocation;
	}

	public long getLogCreateTime() {
		return logCreateTime;
	}

	public void setLogCreateTime(long logCreateTime) {
		this.logCreateTime = logCreateTime;
	}

	@Override
	public String toString() {
		return "TransactionWriteAheadLogMongoBean [transactionWriteAheadLogTypeValue="
				+ transactionWriteAheadLogTypeValue
				+ ", transactionWriteAheadLogTypeDesc="
				+ transactionWriteAheadLogTypeDesc
				+ ", transactionUUID="
				+ transactionUUID
				+ ", ipHost="
				+ ipHost
				+ ", port="
				+ port
				+ ", serviceName="
				+ serviceName
				+ ", logInvocation="
				+ Arrays.toString(logInvocation)
				+ ", logCreateTime="
				+ logCreateTime + "]";
	}
	
	
	
	
}
