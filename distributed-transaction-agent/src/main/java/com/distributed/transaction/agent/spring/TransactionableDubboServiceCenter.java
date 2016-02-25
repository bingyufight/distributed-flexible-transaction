package com.distributed.transaction.agent.spring;

import java.lang.reflect.Method;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


import com.distributed.transaction.api.Transactionable;
import com.distributed.transaction.common.util.AssertUtil;


/**
 * 
 * @author yubing
 *
 */
public class TransactionableDubboServiceCenter implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(TransactionableDubboServiceCenter.class);
	
	private ApplicationContext applicationContext;
	
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.applicationContext = arg0;

	}
	
	public  ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
	}
	
	public  <T> T getBean(String name) {
	    assertContextInjected();
	    return (T) applicationContext.getBean(name);
    }
	
	
	public <T> T getBean(Class<T> requiredType) {
	    assertContextInjected();
	    return applicationContext.getBean(requiredType);
	}
	
	public String getRollBackMethodOftransactionableAnnotationOnMethod(Class<?> requiredType,String methodName,Class<?>[] parameterTypes){
		logger.info("requiredType name:{},methodName:{}",requiredType.getName(),methodName);
		Method method = null;
		try {
             method = requiredType.getDeclaredMethod(methodName, parameterTypes);
             if(method != null){
            	 Transactionable transactionableAnnotation  = method.getAnnotation(Transactionable.class);
            	 if(transactionableAnnotation != null){
            		 return transactionableAnnotation.rollbackMethod();
            	 }
             }
         } catch (Throwable e) {
             return "";
         }
		 return "";
	}
	
	
	private void assertContextInjected(){
		AssertUtil.notNull(applicationContext);
	}



}
