package com.distributed.transaction.api;

import java.io.Serializable;


import com.distributed.transaction.common.Duplicatedable;

/**
 * 
 * @author yubing
 *
 */
public  class TransactionGlobalId implements Duplicatedable<TransactionGlobalId>,Serializable {

	private static final long serialVersionUID = 3557710502344910802L;
	
	private  String globalTransactionUUID;

	public TransactionGlobalId(TransactionGlobalId copyInstance){
		this(copyInstance.globalTransactionUUID);
	}
	
	public TransactionGlobalId(String globalUUid){
		this.globalTransactionUUID = globalUUid;
	}
	
	public TransactionGlobalId(){
		
	}
	
	
	 public String getGlobalTransactionUUID() {
		return globalTransactionUUID;
	}

	@Override
	 public TransactionGlobalId clone() { 
		 return new TransactionGlobalId(this); 
	 }
    

}
