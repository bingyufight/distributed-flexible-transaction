package com.distributed.transaction.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distributed.transaction.api.Participant;
import com.distributed.transaction.api.Transaction;
import com.distributed.transaction.api.TransactionInvocation;
import com.distributed.transaction.api.service.TransactionManagerService;
import com.distributed.transaction.common.util.AssertUtil;


/**
 * 
 * @author yubing
 *
 */



public class ParticipantTracer {
	
	private static final Logger logger = LoggerFactory.getLogger(ParticipantTracer.class);
	
	private TransactionManagerService transactionManagerService;
	
    private ThreadLocal<Participant> parentParticipant = new ThreadLocal<Participant>();
	
	private ParticipantTracer(){
		
	}
	
	private static class ParticipantTracerHolder{
		static ParticipantTracer holderInstance = new ParticipantTracer();
	}
	
	
	public static ParticipantTracer getParticipantTracer(){
		return ParticipantTracerHolder.holderInstance;
	}


	public TransactionManagerService getTransactionManagerService() {
		return transactionManagerService;
	}


	public void setTransactionManagerService(TransactionManagerService transactionManagerService) {
		this.transactionManagerService = transactionManagerService;
	}
	
	public Transaction createTransaction(){
		return transactionManagerService.beginTransaction();
	}
	
	
	public Transaction getTransactionByTransactionUUID(String transactionUUID){
		AssertUtil.notNull(transactionUUID);
		return transactionManagerService.getTransactionByTransactionUUID(transactionUUID);
	}
	
	public void rollback(String transactionUUID){
		AssertUtil.notNull(transactionUUID);
		Transaction transaction = getTransactionByTransactionUUID(transactionUUID);
		transactionManagerService.rollback(transaction);
	}
	
	public void rollback(Transaction transaction){
		AssertUtil.notNull(transaction);
		transactionManagerService.rollback(transaction);
	}
	
	public void enrollParticipant(Transaction transaction,Participant participant){
		AssertUtil.notNull(transaction);
		AssertUtil.notNull(participant);
		
		transactionManagerService.enrollParticipant(transaction, participant);
	}


	public Participant getParentParticipant() {
		return parentParticipant.get();
	}


	public void setParentParticipant(Participant parentParticipant) {
		this.parentParticipant.set(parentParticipant);
	}
	
	public void removeParentParticipant(){
		parentParticipant.remove();
	}
	
	public TransactionInvocation generateTransactionInvocation(Class<?> dubboServiceInterface,String methodName,
			Object[] argumentValues,Class<?>[] argumentTypes){
		return new TransactionInvocation(dubboServiceInterface,methodName,argumentValues,argumentTypes);
	}
	
	public Participant generateParticipant(Transaction transaction,String participantIpHost,int port,
			String participantServiceName,String participantMethodName,TransactionInvocation commitTransactionInvocation,TransactionInvocation rollbackTransactionInvocation){
		return new Participant(transaction.getTransactionGlobalId().getGlobalTransactionUUID(),participantIpHost,port,
				participantServiceName,participantMethodName,commitTransactionInvocation,rollbackTransactionInvocation);
	}
	
	

}
