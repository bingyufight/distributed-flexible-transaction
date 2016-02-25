package com.distributed.transaction.agent.dubbo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.distributed.transaction.agent.ParticipantTracer;
import com.distributed.transaction.agent.spring.TransactionableDubboServiceCenter;
import com.distributed.transaction.api.Participant;
import com.distributed.transaction.api.Transaction;
import com.distributed.transaction.api.TransactionInvocation;
import com.distributed.transaction.api.exception.TransactionRollBackException;
import com.distributed.transaction.api.service.TransactionManagerService;
import com.distributed.transaction.common.CommonResponse;
import com.distributed.transaction.common.constants.DistributeTransactionConstants;
import com.distributed.transaction.common.util.StringUtil;


/**
 * 
 * @author yubing
 *
 */

@Activate(group = { Constants.PROVIDER, Constants.CONSUMER })
public class TransactionFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(TransactionFilter.class);
	
	
	private TransactionableDubboServiceCenter transactionableDubboServiceCenter;
	
    private ParticipantTracer participantTracer;
    

	
	
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		
		String serviceName = invoker.getInterface().getName();
		Class serviceType = invoker.getInterface();
//		String serviceName = invoker.getInterface().getName();
		String transactionableRollbackMethod = "";
		Participant participant = null ;
	
		try{	
			String methodName = invocation.getMethodName();
			Class[] parameterTypes = invocation.getParameterTypes();
			Object[] argumentValues = invocation.getArguments();
		//	logger.info("{}&*{}",serviceName,methodName);
			
			transactionableRollbackMethod = transactionableDubboServiceCenter.getRollBackMethodOftransactionableAnnotationOnMethod(serviceType, methodName, parameterTypes);
			
			
			if(StringUtil.isNullOrEmpty(transactionableRollbackMethod)){
				return invoke(invoker,invocation);
			} else {
				RpcContext context = RpcContext.getContext();
				TransactionInvocation commitInvocation = participantTracer.generateTransactionInvocation(serviceType, methodName, argumentValues, parameterTypes);
				
				TransactionInvocation rollbackInvocation = participantTracer.generateTransactionInvocation(serviceType, transactionableRollbackMethod, argumentValues, parameterTypes);
				String localHostAddress = context.getLocalAddressString();
				int localHostPort = context.getLocalPort();
				
				
				
				if(context.isConsumerSide()){
					Participant parentParticipant = participantTracer.getParentParticipant();
					if(parentParticipant == null){
						Transaction transaction = participantTracer.createTransaction();
						participant = participantTracer.generateParticipant(transaction, localHostAddress, localHostPort, 
								serviceName, methodName, commitInvocation, rollbackInvocation);
						participantTracer.enrollParticipant(transaction, participant);
						
						
					} else {
						Transaction transactionExist = participantTracer.getTransactionByTransactionUUID(parentParticipant.getTransactionUUID());
						participant = participantTracer.generateParticipant(transactionExist, localHostAddress, localHostPort, 
								serviceName, methodName, commitInvocation, rollbackInvocation);
						participantTracer.enrollParticipant(transactionExist, participant);
						
					}
					
				} else if(context.isProviderSide()){
					    String transactionUUID = invocation.getAttachment(DistributeTransactionConstants.TRANSACTION_ID);
		                Transaction transactionExist = participantTracer.getTransactionByTransactionUUID(transactionUUID);
		                participant = participantTracer.generateParticipant(transactionExist, localHostAddress, localHostPort, 
								serviceName, methodName, commitInvocation, rollbackInvocation);
		            	participantTracer.enrollParticipant(transactionExist, participant);
		                
				}
				
				doWorkBeforeInvoke(participant,invocation);
				
				Result result = invoker.invoke(invocation);
		        if (isTriggerTransactionRollback(result)) {
		               throw new RpcException("trigger transaction rollback");
		        }
		        
		        return result;
				
				
			}
			
	
		} catch(RpcException e){
			try{
				if(!StringUtil.isNullOrEmpty(transactionableRollbackMethod)){ //if the dubbo service method have marked the "@Transactionable" annotation,if there is any RpcException during this dubbo service method excuting,we must rollback the transaction
					if(participant != null){
						participantTracer.rollback(participant.getTransactionUUID());
					}
					
				}
			}catch(Exception ex){
				logger.error("rollback the transaction occur error");
			}
			
			throw new RpcException("occur exception,and rollback the transaction");
			
		} finally{
			doWorkAfterInvoke();
		}
	}

	private boolean isTriggerTransactionRollback(Result result){
		CommonResponse commonResponse = (CommonResponse)result.getValue();
	    if (result.getException() != null  || (commonResponse != null && commonResponse.getCode() != DistributeTransactionConstants.DUBBO_SERVICE_RETURN_SUCCESS  ) ) {
	    	return true;
             
	    }
		return false;
	}
    
	private void doWorkBeforeInvoke(Participant participant,Invocation invocation){
		 RpcContext context = RpcContext.getContext();
	     if (context.isProviderSide()) {
	    	 participantTracer.setParentParticipant(participant);
	     }
	     
	     RpcInvocation invocation1 = (RpcInvocation) invocation;
         invocation1.setAttachment(DistributeTransactionConstants.TRANSACTION_ID,
        		 (participant != null && participant.getTransactionUUID() != null) ? participant.getTransactionUUID() : null);
	}
	
	private void doWorkAfterInvoke(){
		participantTracer.removeParentParticipant();
	}
	
	
    

	public TransactionableDubboServiceCenter getTransactionableDubboServiceCenter() {
		return transactionableDubboServiceCenter;
	}



	public void setTransactionableDubboServiceCenter(TransactionableDubboServiceCenter transactionableDubboServiceCenter) {
		this.transactionableDubboServiceCenter = transactionableDubboServiceCenter;
	}



	public ParticipantTracer getParticipantTracer() {
		return participantTracer;
	}



	public void setParticipantTracer(ParticipantTracer participantTracer) {
		this.participantTracer = participantTracer;
	}
	
	 static {
	        logger.info("distributeTransactionAgent filter is loading distribute-transaction-agent.xml file...");
	        String resourceName = "classpath*:distribute-transaction-agent.xml";
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
	                new String[] { resourceName });
	        context.start();
	    }
	
	

}
