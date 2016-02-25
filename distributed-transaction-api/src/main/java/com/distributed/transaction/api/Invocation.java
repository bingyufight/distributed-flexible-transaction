package com.distributed.transaction.api;

import java.util.Map;

/**
 * 
 * @author yubing
 *
 */
public interface Invocation {
	
	Class<?> getTargetClassType();
	
	String 	 getMethodName();
	
	Object[] getArgumentValues();
	
	Class<?>[] getArgumentTypes();
	
	Map<String,Object> getExtraAttachMap();
	
	Object getExtraAttachInfoByKey(String key,Object defaultValue);
	
	void putExtraAttachItem(String key,Object value);
	
}
