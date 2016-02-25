package com.distributed.transaction.service.rollback.compensation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 
 * @author yubing
 *
 */
@Component("rollbackTaskJob") 
public class TransactionRollbackCompensationTaskJob {
	
	private static Logger logger = LoggerFactory.getLogger(TransactionRollbackCompensationTaskJob.class);
	
	@Autowired
	private TransactionRollbackCompensation transactionRollbackCompensation;
	
    @Scheduled(cron = "0 0 23 * * ?")  
    public void rollbackJob() {  
        logger.info("start to excute rollbackJob.....");
        transactionRollbackCompensation.startExecutingRollbackCompensation();
    }  

}
