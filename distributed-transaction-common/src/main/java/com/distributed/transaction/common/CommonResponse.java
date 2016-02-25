package com.distributed.transaction.common;

import java.io.Serializable;


/**
 * 
 * @author yubing
 *
 */
public class CommonResponse<T> implements Serializable {

		private static final long serialVersionUID = 586419597784037066L;
	
	  	private int               code;
	    private int               subcode;
	    private String            msg;
	    private T                 data;

	    public CommonResponse() {
	        this.code = 0;
	        this.subcode = 0;
	        this.msg = "";
	    }

	    public CommonResponse(int code, String msg) {
	        this.code = code;
	        this.msg = msg;
	    }

	    public CommonResponse(int code, String msg, T data) {
	        this.code = code;
	        this.msg = msg;
	        this.data = data;
	    }

	    public CommonResponse(int code, int subCode, String msg, T data) {
	        this.code = code;
	        this.subcode = subCode;
	        this.msg = msg;
	        this.data = data;
	    }

	    public int getCode() {
	        return code;
	    }

	    public void setCode(int code) {
	        this.code = code;
	    }

	    public int getSubcode() {
	        return subcode;
	    }

	    public void setSubcode(int subcode) {
	        this.subcode = subcode;
	    }

	    public String getMsg() {
	        return msg;
	    }

	    public void setMsg(String msg) {
	        this.msg = msg;
	    }

	    public T getData() {
	        return data;
	    }

	    public void setData(T data) {
	        this.data = data;
	    }

		@Override
		public String toString() {
			return "CommonResponse [code=" + code + ", subcode=" + subcode
					+ ", msg=" + msg + ", data=" + data + "]";
		}

	    
		

}
