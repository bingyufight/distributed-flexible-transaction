package com.distributed.transaction.core.repository;

import java.util.List;

import com.distributed.transaction.api.Transaction;


/**
 * 
 * @author yubing
 *
 */
public interface DistributedTransactionRepository {
	
	void saveTransaction(Transaction transaction);
	
	void updateTransaction(Transaction transaction);
	
	public Transaction getTransaction(String transactionUUID);
	
	public void deleteTransaction(Transaction transaction);
	
	public List<Transaction> getRollbackErrorTransaction();

}
