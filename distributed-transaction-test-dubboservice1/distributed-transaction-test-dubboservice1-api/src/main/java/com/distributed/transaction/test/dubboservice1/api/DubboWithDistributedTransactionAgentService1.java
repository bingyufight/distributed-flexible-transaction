package com.distributed.transaction.test.dubboservice1.api;

import com.distributed.transaction.api.Transactionable;
import com.distributed.transaction.common.CommonResponse;

/**
 * 
 * @author yubing
 *
 */
public interface DubboWithDistributedTransactionAgentService1 {
	
	@Transactionable(rollbackMethod = "service1rollback")
	CommonResponse service1();
	
	CommonResponse service1rollback();

}
