package com.distributed.transaction.api.exception;

/**
 * 
 * @author yubing
 *
 */
public class TransactionRollBackException extends RuntimeException {

	private static final long serialVersionUID = 3990042597824697380L;
	
	public TransactionRollBackException(String msg){
		super(msg);
	}

}
