package com.distributed.transaction.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.distributed.transaction.common.constants.DistributeTransactionConstants;
import com.distributed.transaction.common.util.AssertUtil;
import com.distributed.transaction.common.util.UUIDUtil;

/**
 * 
 * @author yubing
 *
 */
public class Transaction implements Serializable {

	private static final long serialVersionUID = 6648691752838557325L;
	
	private final TransactionGlobalId transactionGlobalId;
	
	private TransactionState transactionState;
	
	private final List<Participant> participantList;
	
	private long createTime;
	
	private AtomicInteger retryRollbackCount ; 
	
	
	
	public Transaction(){
		transactionGlobalId = new TransactionGlobalId(UUIDUtil.generateUUID());
		setCreateTime(System.currentTimeMillis());
		participantList = new ArrayList<Participant>();
		transactionState = TransactionState.COMMIT;
		retryRollbackCount= new AtomicInteger(0);
		
	}
	
	
    public void enrollParticipantToTransaction(Participant participant){
    	AssertUtil.notNull(participant);
    	participantList.add(participant);
    }
    
    public List<Participant> getParticipantList(){
    	return participantList;
    }


	public TransactionState getTransactionState() {
		return transactionState;
	}


	public void setTransactionState(TransactionState transactionState) {
		this.transactionState = transactionState;
	}


	public TransactionGlobalId getTransactionGlobalId() {
		return transactionGlobalId;
	}


	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}


	
	public AtomicInteger getRetryRollbackCount() {
		return retryRollbackCount;
	}


	public int incrementRetryRollbackCount() {
		return retryRollbackCount.incrementAndGet();
	}
	

	@Override
	public String toString() {
		return "Transaction [transactionGlobalId=" + transactionGlobalId
				+ ", transactionState=" + transactionState
				+ ", participantList=" + participantList + ", createTime="
				+ createTime + ", retryRollbackCount=" + retryRollbackCount
				+ "]";
	}
	
	
}
