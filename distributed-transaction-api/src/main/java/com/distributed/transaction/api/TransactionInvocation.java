package com.distributed.transaction.api;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.distributed.transaction.common.util.AssertUtil;


/**
 * 
 * @author yubing
 *
 */
public class TransactionInvocation implements Invocation,Serializable{

	private static final long serialVersionUID = 7661979164428004142L;

	private  Class targetClassType;
	
	private  String methodName;
	
	private  Object[] argumentValues;
	
	private  Class[] argumentTypes;
	
	private Map<String,Object> extraAttachMap;
	
	
	public TransactionInvocation(){
		
	}
	
	public TransactionInvocation(Class targetClassType,String methodName,Object[] argumentValues,Class[] argumentTypes){
		this.targetClassType = targetClassType;
		this.methodName = methodName;
		this.argumentValues = argumentValues;
		this.argumentTypes = argumentTypes;
		extraAttachMap = new ConcurrentHashMap<String,Object>();
	}
	
	
	
	
	@Override
	public Class getTargetClassType() {
		return targetClassType;
	}





	@Override
	public String getMethodName() {
		return methodName;
	}

	@Override
	public Object[] getArgumentValues() {
		return argumentValues;
	}

	@Override
	public Class<?>[] getArgumentTypes() {
		return argumentTypes;
	}

	@Override
	public Map<String, Object> getExtraAttachMap() {
		return extraAttachMap;
	}

	@Override
	public Object getExtraAttachInfoByKey(String key, Object defaultValue) {
		AssertUtil.notNull(key);
		return extraAttachMap.get(key);
		
	}



	@Override
	public void putExtraAttachItem(String key, Object value) {
		AssertUtil.notNull(key);
		AssertUtil.notNull(value);
		extraAttachMap.put(key, value);
		
	}

}
