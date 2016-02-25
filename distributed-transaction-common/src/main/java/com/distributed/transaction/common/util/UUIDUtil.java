package com.distributed.transaction.common.util;

import java.util.UUID;


/**
 * 
 * @author yubing
 *
 */
public class UUIDUtil {
	
	public static String generateUUID(){
		String uuid = UUID.randomUUID().toString();
		String uuIdString = uuid.replaceAll("-", "");
		return uuIdString;
	}

}
