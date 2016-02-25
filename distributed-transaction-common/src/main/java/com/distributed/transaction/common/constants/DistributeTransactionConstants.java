package com.distributed.transaction.common.constants;


/**
 * 
 * @author yubing
 *
 */
public class DistributeTransactionConstants {
	
	public static final String TRANSACTION_ID = "transaction_id";
	
	public static final String PARTICIPANT_ID = "participant_id";
	
	public static final String PARENT_PARTICIPANT_ID = "parent_participant_id";
	
	public static final int DUBBO_SERVICE_RETURN_SUCCESS = 0;
	
	public static final int MAX_RETRY_ROLLBACK_COUNT = 3;
	
	public final static int  THREAD_POOL_SIZE = 40;
	
	public final static String DISTRIBUTE_TRANSACTION_POOL = "DISTRIBUTE_TRANSACTION_POOL";

}
