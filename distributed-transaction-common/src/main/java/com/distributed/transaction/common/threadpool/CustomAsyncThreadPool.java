package com.distributed.transaction.common.threadpool;

import java.util.concurrent.ExecutorService;

import com.distributed.transaction.common.constants.DistributeTransactionConstants;



/**
 * 
 * @author yubing
 *
 */
public class CustomAsyncThreadPool {
	
	private ExecutorService service = null;
    private CustomAsyncThreadPool(){
        service = new CustomThreadPoolExecutor(DistributeTransactionConstants.DISTRIBUTE_TRANSACTION_POOL,false,DistributeTransactionConstants.THREAD_POOL_SIZE);
    }
    
    
    private static class CustomAsyncThreadPoolHolder{
     
        private static CustomAsyncThreadPool instance = new CustomAsyncThreadPool();
    }
    
    public static CustomAsyncThreadPool getInstance(){
        return CustomAsyncThreadPoolHolder.instance;
    }
    
    public void submitTask(Runnable task) {
        service.execute(task);
    }

}
