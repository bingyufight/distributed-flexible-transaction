package com.distributed.transaction.core.mongo.pojo;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author yubing
 *
 */

@Document(collection="transaction_mongo_bean")
public class TransactionMongoBean {
	
	private ObjectId id;
	
	private String transactionUUID;
	
	private int transactionState;
	
	private byte[] transactionContent;
	
	

	public String getTransactionUUID() {
		return transactionUUID;
	}



	public void setTransactionUUID(String transactionUUID) {
		this.transactionUUID = transactionUUID;
	}



	public int getTransactionState() {
		return transactionState;
	}



	public void setTransactionState(int transactionState) {
		this.transactionState = transactionState;
	}



	public byte[] getTransactionContent() {
		return transactionContent;
	}



	public void setTransactionContent(byte[] transactionContent) {
		this.transactionContent = transactionContent;
	}


	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "TransactionMongoBean [id=" + id + ", transactionUUID="
				+ transactionUUID + ", transactionState=" + transactionState
				+ ", transactionContent=" + Arrays.toString(transactionContent)
				+ "]";
	}
	
	
	
	
}
