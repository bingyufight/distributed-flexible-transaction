package com.distributed.transaction.common.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yubing
 *
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
	
	private static Logger logger = LoggerFactory.getLogger(CustomThreadPoolExecutor.class);
	
	private static CustomRejectedExecutionHandlerImpl rejectedExecutionHandler = new CustomRejectedExecutionHandlerImpl();
	
	
	public CustomThreadPoolExecutor(String threadNamePrefix,boolean dameon,int nThreadPoolSize){
		this(threadNamePrefix,dameon,nThreadPoolSize,nThreadPoolSize,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
		logger.info("init customThreadPoolExecutor,nThreadPoolSize:{}",nThreadPoolSize);
	}

	public CustomThreadPoolExecutor(String threadNamePrefix,boolean dameon,int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				new CustomNamedThreadFactory(threadNamePrefix,dameon), rejectedExecutionHandler);
	}
	
	

}
