package com.distributed.transaction.service.dubbo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.Result;
import com.distributed.transaction.api.Participant;
import com.distributed.transaction.api.TransactionInvocation;
import com.distributed.transaction.api.Transactionable;
import com.distributed.transaction.common.CommonResponse;
import com.distributed.transaction.common.constants.DistributeTransactionConstants;
import com.distributed.transaction.common.util.AssertUtil;


/**
 * 
 * @author yubing
 *
 */
public class ParticipantRollbackUtil {
	
	private static Map<String,ReferenceConfig<?>> referenceConfigMap = new ConcurrentHashMap<String,ReferenceConfig<?>>();
	
	
	
	
	public static boolean rollback(Participant participant){
		AssertUtil.notNull(participant);
		try{
			
			if(participant != null && participant.getRollbackTransactionInvocation() != null ){
				ReferenceConfig referenceConfig;
				if(referenceConfigMap.containsKey(participant.getRollbackTransactionInvocation().getTargetClassType().getName())){
					referenceConfig = referenceConfigMap.get(participant.getRollbackTransactionInvocation().getTargetClassType().getName());
				} else {
				    referenceConfig = new ReferenceConfig();
				    referenceConfig.setApplication(ParticipantRollbackConsumerConfig.getSingleInstance().getApplicationConfig());
				    referenceConfig.setRegistry(ParticipantRollbackConsumerConfig.getSingleInstance().getRegistryConfig());
				    referenceConfig.setInterface(participant.getRollbackTransactionInvocation().getTargetClassType());
				}
				
				Result result = (Result)invokeDubboMethod(referenceConfig,participant.getRollbackTransactionInvocation());
				if(result != null && result.hasException()){
					return false;
					
				} else {
					CommonResponse commonResponse = (CommonResponse)result.getValue();
					if(DistributeTransactionConstants.DUBBO_SERVICE_RETURN_SUCCESS == commonResponse.getCode()){
						return true;
					}
				}
				
				
				
			}
		}catch(Throwable e){
			return false;
		}
		return false;
	}
	
	private static Method getMethodByNameAndParameterTypes(Class<?> requiredType,String methodName,Class<?>[] parameterTypes){
		Method method = null;
		try {
             method = requiredType.getDeclaredMethod(methodName, parameterTypes);
             if(method != null){
            	 return method;
             }
         } catch (Throwable e) {
             return null;
         }
		 return null;
	}
	
	
	private static <T> T invokeDubboMethod(ReferenceConfig referenceConfig,TransactionInvocation transactionInvocation){
		Object targetInstance = referenceConfig.get() ;
		Method invokeMethod = getMethodByNameAndParameterTypes(transactionInvocation.getTargetClassType(),transactionInvocation.getMethodName(),transactionInvocation.getArgumentTypes());
		try{
			T result =(T)invokeMethod.invoke(targetInstance, transactionInvocation.getArgumentValues());
			return result;
		}catch(Throwable e){
			//to do
		}
		
		return null;
	}
	

}
