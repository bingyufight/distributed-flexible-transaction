package com.distributed.transaction.test.dubboservice1.provider;

import org.springframework.stereotype.Service;


import com.distributed.transaction.common.CommonResponse;
import com.distributed.transaction.test.dubboservice1.api.DubboWithDistributedTransactionAgentService1;

/**
 * 
 * @author yubing
 *
 */
@Service("dubboWithDistributedTransactionAgentService1")
public class TestTransactionAgentService1Impl implements
		DubboWithDistributedTransactionAgentService1 {


	public CommonResponse service1() {
		System.out.println("execute service1-----");
		
		return new CommonResponse();
		
	}

	
	public CommonResponse service1rollback() {
		System.out.println("execute service1rollback-----");
		return new CommonResponse();

	}

}
