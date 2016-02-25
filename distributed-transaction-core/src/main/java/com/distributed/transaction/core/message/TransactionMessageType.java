package com.distributed.transaction.core.message;

/**
 * 
 * @author yubing
 * 
 * 
 *todo add a distributed event system,
 *
 */

//TODO need to add a distributed event system,not used in this version.
public enum TransactionMessageType {
	
	CAN_COMMIT_REQUEST(1,"coordinator send commit request message"),
	PARTICIPANT_YES(2,"participant send yes response in the phase of can commit"),
	PARTICIPANT_NO(3,"participant send no response in the phase of can commit"),
	PRE_COMMIT(4,"coordinator send pre commit message to all participants"),
	PARTICIPANT_ACK_PRE_COMMIT(5,"participant send ack message in the phase of pre commit"),
	PARTICIPANT_ABORT_PRE_COMMIT(6,"participant send abort message in the phase of pre commit"),
	ABORT(7,"coordinator send abort message to all participants"),
	DO_COMMIT(8,"coordinator send do commit to all participants"),
	PARTICIPANT_ACK_DO_COMMIT(9,"participant send ack message in the phase of do commit"),
	PARTICIPANT_ABORT_DO_COMMIT(10,"participant send abort message in the phase of do commit");
	
	
	
	private final int value;
	private final String desc;
	
	private TransactionMessageType(int value,String desc){
		this.value = value;
		this.desc = desc;
		
	}
	
	public int getValue(){
		return value;
	}
	
	public String getDesc(){
		return desc;
	}
	
	
	
	
	

}
