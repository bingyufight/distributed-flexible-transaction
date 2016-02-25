package com.distributed.transaction.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 
 * @author yubing
 *
 */
public class SerializeUtil {
	
	public static byte[] serializeToByteArray(Object object){
		AssertUtil.notNull(object, "serialize object can not be null");
		ObjectOutputStream objectOutputStream ;
		ByteArrayOutputStream byteArrayOutputStream;
		try{
			//begin to do serializing
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			
			byte[] bytes = byteArrayOutputStream.toByteArray();
			return bytes;
		}catch(Exception e){
			return null;
		}
	}
	
	
	public static Object byteArrayDeserializedToObject(byte[] bytes){
		AssertUtil.notNull(bytes, "bytes array cannot be null");
		ByteArrayInputStream byteArrayInputStream;
		ObjectInputStream objectInputStream;
		try{
			byteArrayInputStream = new ByteArrayInputStream(bytes);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return objectInputStream.readObject();
		}catch(Exception e){
			return null;
		}
	}

}
