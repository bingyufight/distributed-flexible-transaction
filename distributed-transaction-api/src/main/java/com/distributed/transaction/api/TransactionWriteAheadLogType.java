package com.distributed.transaction.api;


/**
 * 
 * @author yubing
 *
 */

public enum TransactionWriteAheadLogType {
	
	LOG_PARTICIPANT_ENROLL_BEFORE_COMMIT(1,"log particiant dubbo invoke enroll in the whole transaction"),
	LOG_PARTICIPANT_ROLLBACK_BEFORE_ACTIVE_ROLLBACK(2,"log participant dubbo invoke rollback before the transaction active calling rollback"),
	LOG_PARTICIPANT_ROLLBACK_BY_ROLLBACK_COMPENSATION_JOB(3,"log participant dubbo invoke rollback before the compensation job trigger the transaction rollback");
	
	private final int value;
	private final String desc;
	
	private TransactionWriteAheadLogType(int value,String desc){
		this.value = value;
		this.desc = desc;
		
	}
	
	public int getValue(){
		return value;
	}
	
	public String getDesc(){
		return desc;
	}

	
	public static TransactionWriteAheadLogType getTransactionWriteAheadLogTypeByValue(int value){
		switch(value){
			case 1:
				return LOG_PARTICIPANT_ENROLL_BEFORE_COMMIT;
			case 2:
				return LOG_PARTICIPANT_ROLLBACK_BEFORE_ACTIVE_ROLLBACK;
			case 3:
				return LOG_PARTICIPANT_ROLLBACK_BY_ROLLBACK_COMPENSATION_JOB;
			default:
				return null;
		}
	}
}
