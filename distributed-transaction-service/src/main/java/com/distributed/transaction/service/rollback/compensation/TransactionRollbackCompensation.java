package com.distributed.transaction.service.rollback.compensation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.distributed.transaction.api.Participant;
import com.distributed.transaction.api.ParticipantState;
import com.distributed.transaction.api.Transaction;
import com.distributed.transaction.api.TransactionState;
import com.distributed.transaction.api.TransactionWriteAheadLogType;
import com.distributed.transaction.common.constants.DistributeTransactionConstants;
import com.distributed.transaction.core.repository.DistributedTransactionRepository;
import com.distributed.transaction.core.wal.log.AsyncMongoTransactionWriteAheadLogComponent;
import com.distributed.transaction.service.dubbo.util.ParticipantRollbackUtil;



@Component
public class TransactionRollbackCompensation {
	
	private static Logger logger = LoggerFactory.getLogger(TransactionRollbackCompensation.class);
	
	@Autowired
	private DistributedTransactionRepository mongoDistributedTransactionRepository;
	
	@Autowired
	private AsyncMongoTransactionWriteAheadLogComponent asyncMongoTransactionWriteAheadLogComponent;
	
	
	//TODO will add distributed Lock in this method.Refer to my another component:    https://github.com/bingyufight/DistributeLockGenerator
	public void startExecutingRollbackCompensation(){
		List<Transaction> transactionList = mongoDistributedTransactionRepository.getRollbackErrorTransaction();
		if(transactionList != null && transactionList.size() > 0){
			logger.info("rollbackTransaction list size:{}",transactionList.size());
			for(Transaction rollbackTransaction : transactionList){
				if(rollbackTransaction.getTransactionState() == TransactionState.ROLLBACK_FAIL && rollbackTransaction.getRetryRollbackCount().intValue() < DistributeTransactionConstants.MAX_RETRY_ROLLBACK_COUNT){
					rollbackTransaction.incrementRetryRollbackCount();
					boolean isParticipantRollbackHasOccurError = false;
					for(Participant participant : rollbackTransaction.getParticipantList()){
						if(participant != null && (participant.getParticipantState() == ParticipantState.PARTICIPANT_ROLLBACK_FAIL)){
							asyncMongoTransactionWriteAheadLogComponent.writeAheadLog(participant, TransactionWriteAheadLogType.LOG_PARTICIPANT_ROLLBACK_BY_ROLLBACK_COMPENSATION_JOB);
							boolean weatherRollbackSuccess = ParticipantRollbackUtil.rollback(participant);
							if(weatherRollbackSuccess){
								participant.setParticipantState(ParticipantState.PARTICIPANT_ROLLBACK_SUCCESS);
							} else {
								participant.setParticipantState(ParticipantState.PARTICIPANT_ROLLBACK_FAIL);
								isParticipantRollbackHasOccurError = true;
							}
						}
					}
					
					if(isParticipantRollbackHasOccurError == false){
						rollbackTransaction.setTransactionState(TransactionState.ROLLBACK_SUCCESS);
					} else {
						rollbackTransaction.setTransactionState(TransactionState.ROLLBACK_FAIL);
					}
					
					mongoDistributedTransactionRepository.updateTransaction(rollbackTransaction);
					
					
				}
			}
		}
		
	}
	
	

}
