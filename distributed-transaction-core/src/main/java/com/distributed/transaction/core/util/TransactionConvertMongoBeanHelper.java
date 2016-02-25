package com.distributed.transaction.core.util;

import com.distributed.transaction.api.Transaction;
import com.distributed.transaction.common.util.AssertUtil;
import com.distributed.transaction.common.util.SerializeUtil;
import com.distributed.transaction.core.mongo.pojo.TransactionMongoBean;

/**
 * 
 * @author yubing
 *
 */
public class TransactionConvertMongoBeanHelper {
	
	public static TransactionMongoBean transactionConvertToMongoBean(Transaction transaction){
		AssertUtil.notNull(transaction);
		TransactionMongoBean transactionMongoBean = new TransactionMongoBean();
		transactionMongoBean.setTransactionUUID(transaction.getTransactionGlobalId().getGlobalTransactionUUID());
		transactionMongoBean.setTransactionState(transaction.getTransactionState().getValue());
		transactionMongoBean.setTransactionContent(SerializeUtil.serializeToByteArray(transaction));
		return transactionMongoBean;
	}
	
	public static Transaction transactionMongoBeanConvertToTransaction(TransactionMongoBean transactionMongoBean){
		if(transactionMongoBean != null){
			Transaction transaction = (Transaction)SerializeUtil.byteArrayDeserializedToObject(transactionMongoBean.getTransactionContent());
			return transaction;
		}
		return null;
	}

}
