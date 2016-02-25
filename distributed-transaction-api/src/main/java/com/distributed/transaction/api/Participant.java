package com.distributed.transaction.api;

import java.io.Serializable;

import com.distributed.transaction.common.util.UUIDUtil;

/**
 * 
 * @author yubing
 *
 */
public class Participant implements Serializable{

	private static final long serialVersionUID = -4512371127490746819L;
	
	private  String transactionUUID;
	
	private  ParticipantId participantId;
	
	private  String participantIpHost;
	
	private   int participantPort;
	
	private  String participantServiceName;
	
	private String participantMethodName;
	
	private  TransactionInvocation commitTransactionInvcoation;
	
	private  TransactionInvocation rollbackTransactionInvocation;
	
	private ParticipantState participantState;
	
	public Participant(){
		
	}
		
	public Participant(String transactionUUID,String participantIpHost,int port,
			String participantServiceName,String participantMethodName,TransactionInvocation commitTransactionInvocation,TransactionInvocation rollbackTransactionInvocation){
		this.transactionUUID = transactionUUID;
		this.participantId = new ParticipantId(participantIpHost,port,UUIDUtil.generateUUID());
		this.participantIpHost = participantIpHost;
		this.participantPort = port;
		this.participantServiceName = participantServiceName;
		this.participantMethodName = participantMethodName;
		this.commitTransactionInvcoation = commitTransactionInvocation;
		this.rollbackTransactionInvocation = rollbackTransactionInvocation;
		this.setParticipantState(ParticipantState.PARTICIPANT_EXECUTED);
	}

	public ParticipantId getParticipantId() {
		return participantId;
	}

	public String getParticipantIpHost() {
		return participantIpHost;
	}

	public int getParticipantPort() {
		return participantPort;
	}
	
	

	public String getParticipantServiceName() {
		return participantServiceName;
	}

	public TransactionInvocation getCommitTransactionInvcoation() {
		return commitTransactionInvcoation;
	}

	public TransactionInvocation getRollbackTransactionInvocation() {
		return rollbackTransactionInvocation;
	}

	public String getTransactionUUID() {
		return transactionUUID;
	}

	public String getParticipantMethodName() {
		return participantMethodName;
	}
	

	public ParticipantState getParticipantState() {
		return participantState;
	}

	public void setParticipantState(ParticipantState participantState) {
		this.participantState = participantState;
	}

	@Override
	public String toString() {
		return "Participant [transactionUUID=" + transactionUUID
				+ ", participantId=" + participantId + ", participantIpHost="
				+ participantIpHost + ", participantPort=" + participantPort
				+ ", participantServiceName=" + participantServiceName
				+ ", participantMethodName=" + participantMethodName
				+ ", commitTransactionInvcoation="
				+ commitTransactionInvcoation
				+ ", rollbackTransactionInvocation="
				+ rollbackTransactionInvocation + ", participantState="
				+ participantState + "]";
	}
	
    

	
	
	
	
	

}
