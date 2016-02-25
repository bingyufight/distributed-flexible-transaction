package com.distributed.transaction.common.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 
 * @author yubing
 *
 */
public class CustomNamedThreadFactory implements ThreadFactory {

	
	private final String threadFactoryPrefixName;
	
	public final boolean daemo;
	
	private final AtomicInteger threadSortedNum = new AtomicInteger(1);
	
	
	public CustomNamedThreadFactory(String threadFactoryPrefixName,boolean daemo){
		this.threadFactoryPrefixName = threadFactoryPrefixName + "_thread_";
		this.daemo =daemo;
		
	}
	
	@Override
	public Thread newThread(Runnable r) {
		String name = threadFactoryPrefixName + threadSortedNum.getAndIncrement();
        Thread returnThread = new Thread(r,name);
        returnThread.setDaemon(daemo);
        return returnThread;
	}

}
