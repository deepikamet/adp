package com.deepika.adpinterview.coinmachine.exception;

public class CustomException extends Throwable{
	
	

	public CustomException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomException(String errorcode, String errormessage) {
		super();
		this.errorcode = errorcode;
		this.errormessage = errormessage;
	}
	String errorcode;
	String errormessage;
	
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	
	
}
