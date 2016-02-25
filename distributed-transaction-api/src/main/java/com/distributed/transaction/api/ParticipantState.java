package com.distributed.transaction.api;


/**
 * 
 * @author yubing
 *
 */
public enum ParticipantState {
	PARTICIPANT_EXECUTED(1),
	PARTICIPANT_ROLLBACK_SUCCESS(4),
	PARTICIPANT_ROLLBACK_FAIL(5);
	
	private ParticipantState(int value){
		this.value = value;
	}
	
	private final int value;
	
	public int getValue(){
		return value;
	}
}
