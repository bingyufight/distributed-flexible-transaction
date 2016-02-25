package com.distributed.transaction.api.service;

import com.distributed.transaction.api.Participant;
import com.distributed.transaction.api.Transaction;

/**
 * 
 * @author yubing
 *
 */
public interface TransactionManagerService {
	
	public Transaction beginTransaction();
	
	public void rollback(Transaction transaction);
	
	public void enrollParticipant(Transaction transaction,Participant participant);
	
	public Transaction getTransactionByTransactionUUID(String transactionUUID);

}
