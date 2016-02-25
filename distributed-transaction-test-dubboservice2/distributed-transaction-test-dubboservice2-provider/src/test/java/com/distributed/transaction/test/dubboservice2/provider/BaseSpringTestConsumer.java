package com.distributed.transaction.test.dubboservice2.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.distributed.transaction.test.dubboservice1.api.DubboWithDistributedTransactionAgentService1;
import com.distributed.transaction.test.dubboservice2.api.DubboWithDistributedTransactionAgentService2;


/**
 * 
 * @author yubing
 *
 */
@ContextConfiguration(locations = { "classpath:consumer-dubbo-client.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseSpringTestConsumer {
	
	
	@Autowired
	DubboWithDistributedTransactionAgentService1 dubboWithDistributedTransactionAgentService1;
	
	@Autowired
	DubboWithDistributedTransactionAgentService2 dubboWithDistributedTransactionAgentService2;
	
	@Test
	public void testTransaction(){
		Assert.notNull(dubboWithDistributedTransactionAgentService1);
		System.out.println("ok1");
		Assert.notNull(dubboWithDistributedTransactionAgentService2);
		System.out.println("ok2");
		dubboWithDistributedTransactionAgentService2.service2();
	}


}
