package com.distributed.transaction.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;


/**
 * 
 * @author yubing
 *
 */
public class ObjectHelper {
	
	
	public static boolean isNotNullAndEmpty(Object object) throws IllegalArgumentException{
		return !isNullOrEmpty(object);
	}
	

    public static boolean isNullOrEmpty(Object object)  {
        if(object == null) return true;

        
        if(object instanceof Map) {
            if(((Map)object).isEmpty()){
                return true;
            }
        } else if(object instanceof Collection) {
            if(((Collection)object).isEmpty()){
                return true;
            }
        } else if(object.getClass().isArray()) {
            if(Array.getLength(object) == 0){
                return true;
            }
        } else if(object instanceof String) {
            if(((String)object).length() == 0){
                return true;
            }
        }else {
            return false;
        }

        return false;
    }

	

}
