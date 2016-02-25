package com.distributed.transaction.core.util;

import com.distributed.transaction.api.TransactionInvocation;
import com.distributed.transaction.api.TransactionWriteAheadLog;
import com.distributed.transaction.api.TransactionWriteAheadLogType;
import com.distributed.transaction.common.util.AssertUtil;
import com.distributed.transaction.common.util.SerializeUtil;
import com.distributed.transaction.core.mongo.pojo.TransactionWriteAheadLogMongoBean;

public class TransactionWriteAheadLogConvertMongoBeanHelper {
	
	public static TransactionWriteAheadLogMongoBean transactionWriteAheadLogConvertToMongoBean(TransactionWriteAheadLog transactionWriteAheadLog){
		AssertUtil.notNull(transactionWriteAheadLog);
		TransactionWriteAheadLogMongoBean transactionWriteAheadLogMongoBean = new TransactionWriteAheadLogMongoBean();
		transactionWriteAheadLogMongoBean.setIpHost(transactionWriteAheadLog.getIpHost());
		transactionWriteAheadLogMongoBean.setLogCreateTime(transactionWriteAheadLog.getLogCreateTime());
		transactionWriteAheadLogMongoBean.setLogInvocation(SerializeUtil.serializeToByteArray(transactionWriteAheadLog.getLogInvocation()));
		transactionWriteAheadLogMongoBean.setPort(transactionWriteAheadLog.getPort());
		transactionWriteAheadLogMongoBean.setServiceName(transactionWriteAheadLog.getServiceName());
		transactionWriteAheadLogMongoBean.setTransactionUUID(transactionWriteAheadLog.getTransactionUUID());
		transactionWriteAheadLogMongoBean.setTransactionWriteAheadLogTypeDesc(transactionWriteAheadLog.getTransactionWriteAheadLogType().getDesc());
		transactionWriteAheadLogMongoBean.setTransactionWriteAheadLogTypeValue(transactionWriteAheadLog.getTransactionWriteAheadLogType().getValue());
		return transactionWriteAheadLogMongoBean;
		
	}
	
	
	public static TransactionWriteAheadLog transactionWriteAheadLogMongoBeanConvertToWriteAheadLog(TransactionWriteAheadLogMongoBean transactionWriteAheadLogMongoBean){
		AssertUtil.notNull(transactionWriteAheadLogMongoBean);
		TransactionInvocation logInvocation = (TransactionInvocation) SerializeUtil.byteArrayDeserializedToObject( transactionWriteAheadLogMongoBean.getLogInvocation());
		TransactionWriteAheadLogType transactionWriteAheadLogType = TransactionWriteAheadLogType.getTransactionWriteAheadLogTypeByValue(transactionWriteAheadLogMongoBean.getTransactionWriteAheadLogTypeValue());
		
		TransactionWriteAheadLog transactionWriteAheadLog = new TransactionWriteAheadLog(transactionWriteAheadLogType,transactionWriteAheadLogMongoBean.getIpHost(),transactionWriteAheadLogMongoBean.getPort(),
				transactionWriteAheadLogMongoBean.getServiceName(),logInvocation,transactionWriteAheadLogMongoBean.getTransactionUUID(),transactionWriteAheadLogMongoBean.getLogCreateTime());
		
	    return transactionWriteAheadLog;
	}

}
