package com.distributed.transaction.service.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.distributed.transaction.api.Participant;
import com.distributed.transaction.api.ParticipantState;
import com.distributed.transaction.api.Transaction;
import com.distributed.transaction.api.TransactionState;
import com.distributed.transaction.api.TransactionWriteAheadLogType;
import com.distributed.transaction.api.service.TransactionManagerService;
import com.distributed.transaction.common.util.AssertUtil;
import com.distributed.transaction.core.repository.DistributedTransactionRepository;
import com.distributed.transaction.core.wal.log.AsyncMongoTransactionWriteAheadLogComponent;
import com.distributed.transaction.service.dubbo.util.ParticipantRollbackUtil;

/**
 * 
 * @author yubing
 *
 */

@Service("transactionManagerService")
public class TransactionManagerServiceImpl implements TransactionManagerService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionManagerServiceImpl.class);
	
	@Autowired
	private DistributedTransactionRepository mongoDistributedTransactionRepository;
	
	@Autowired
	private AsyncMongoTransactionWriteAheadLogComponent asyncMongoTransactionWriteAheadLogComponent;
	
	@Override
	public Transaction beginTransaction() {
		logger.info("start executing beginTransaction.....");
		Transaction transaction = new Transaction();
		mongoDistributedTransactionRepository.saveTransaction(transaction);
		return transaction;
	}

	@Override
	public void rollback(Transaction transaction) {
		AssertUtil.notNull(transaction);
		logger.info("start executing rollback....,transactionUUID:{}",transaction.getTransactionGlobalId().getGlobalTransactionUUID());
		boolean isParticipantRollbackHasOccurError = false;
		for(Participant participant : transaction.getParticipantList()){
			asyncMongoTransactionWriteAheadLogComponent.writeAheadLog(participant, TransactionWriteAheadLogType.LOG_PARTICIPANT_ROLLBACK_BEFORE_ACTIVE_ROLLBACK);
			boolean weatherRollbackSuccess = ParticipantRollbackUtil.rollback(participant);
			if(weatherRollbackSuccess){
				participant.setParticipantState(ParticipantState.PARTICIPANT_ROLLBACK_SUCCESS);
			} else {
				participant.setParticipantState(ParticipantState.PARTICIPANT_ROLLBACK_FAIL);
				isParticipantRollbackHasOccurError = true;
			}
		}
		if(isParticipantRollbackHasOccurError){
			transaction.setTransactionState(TransactionState.ROLLBACK_FAIL);
		} else{
			transaction.setTransactionState(TransactionState.ROLLBACK_SUCCESS);
		}
		
		mongoDistributedTransactionRepository.updateTransaction(transaction);
		
	}

	@Override
	public void enrollParticipant(Transaction transaction, Participant participant) {
		AssertUtil.notNull(transaction);
		AssertUtil.notNull(participant);
		asyncMongoTransactionWriteAheadLogComponent.writeAheadLog(participant, TransactionWriteAheadLogType.LOG_PARTICIPANT_ENROLL_BEFORE_COMMIT);
		transaction.enrollParticipantToTransaction(participant);
		mongoDistributedTransactionRepository.updateTransaction(transaction);

	}

	@Override
	public Transaction getTransactionByTransactionUUID(String transactionUUID) {
		AssertUtil.notNull(transactionUUID);
		return mongoDistributedTransactionRepository.getTransaction(transactionUUID);
	}

}
