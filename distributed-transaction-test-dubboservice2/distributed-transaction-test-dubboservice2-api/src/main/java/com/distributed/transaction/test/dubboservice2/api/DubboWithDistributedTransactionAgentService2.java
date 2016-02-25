package com.distributed.transaction.test.dubboservice2.api;

import com.distributed.transaction.api.Transactionable;
import com.distributed.transaction.common.CommonResponse;


/**
 * 
 * @author yubing
 *
 */
public interface DubboWithDistributedTransactionAgentService2 {
	
	@Transactionable(rollbackMethod = "service2rollback")
	CommonResponse service2();
	
	CommonResponse service2rollback();
}
