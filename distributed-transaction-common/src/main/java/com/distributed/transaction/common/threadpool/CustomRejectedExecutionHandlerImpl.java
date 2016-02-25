package com.distributed.transaction.common.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yubing
 *
 */
public class CustomRejectedExecutionHandlerImpl implements
		RejectedExecutionHandler {

	private static Logger logger = LoggerFactory.getLogger(CustomRejectedExecutionHandlerImpl.class);
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		logger.info("AsyncThreadPool occur rejected,must handle this issue,runnable:{} is rejected",r);
		if (!executor.isShutdown()) {  //not giving up any runnable task
            r.run();
        }
	}

}
