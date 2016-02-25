package com.distributed.transaction.common.util;


/**
 * 
 * @author yubing
 *
 */
public class AssertUtil {
	
	 private AssertUtil(){
		 
	 }
	
	 public static void notNull(Object obj, String message) {
	     if (obj == null) {
	         throw new IllegalArgumentException(message);
	     }
	 }
	 
	 public static void notNull(Object obj){
		 if(obj == null){
			 throw new IllegalArgumentException("argument invalid,Please check");
		 }
	 }
	 
	 public static void checkConditionArgument(boolean condition,String message){
		 if(!condition){
			 throw new IllegalArgumentException(message);
		 }
	 }

}
