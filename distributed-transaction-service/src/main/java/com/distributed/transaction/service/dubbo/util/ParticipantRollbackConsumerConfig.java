package com.distributed.transaction.service.dubbo.util;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.distributed.transaction.common.util.ConfigUtil;

/**
 * 
 * @author yubing
 *
 */
public class ParticipantRollbackConsumerConfig {
	
	private final ApplicationConfig applicationConfig;
	
	private final RegistryConfig registryConfig;

	

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}


	public RegistryConfig getRegistryConfig() {
		return registryConfig;
	}


	
	private ParticipantRollbackConsumerConfig(){
		String applicationName = ConfigUtil.getProperty("transaction-servie.properties", "transaction.application.name");
		String applicationOwner = ConfigUtil.getProperty("transaction-servie.properties", "transaction.application.owner");
		String registryProtocol = ConfigUtil.getProperty("transaction-servie.properties", "transaction.registry.protocol");
		String registryAddress = ConfigUtil.getProperty("transaction-servie.properties", "transaction.registry.address");

		applicationConfig = new ApplicationConfig();
		applicationConfig.setName(applicationName);
		applicationConfig.setOwner(applicationOwner);
		registryConfig = new RegistryConfig();
		registryConfig.setProtocol(registryProtocol);
		registryConfig.setAddress(registryAddress);
		
	}
	
	private static class ParticipantRollbackConsumerConfigHolder{
		static ParticipantRollbackConsumerConfig participantRollbackConsumerConfig = new ParticipantRollbackConsumerConfig();
	}
	
	public static  ParticipantRollbackConsumerConfig getSingleInstance(){
		return ParticipantRollbackConsumerConfigHolder.participantRollbackConsumerConfig;
	}

}
