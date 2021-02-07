package com.deepika.adpinterview.coinmachine.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ExceptionHandler(CustomException.class)
	@ResponseBody
	protected ErrorResponse handleConflict(HttpServletResponse resp ,CustomException ex) {
		if(ex.getErrorcode().equals("1002"))
			resp.setStatus(HttpStatus.BAD_REQUEST.value());
		else if(ex.getErrorcode().equals("1003"))
			resp.setStatus(HttpStatus.NOT_FOUND.value());
		return new ErrorResponse(ex.getErrorcode(),ex.getErrormessage());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	protected ErrorResponse handleConflictHttpMessageNotReadableException(HttpServletResponse resp ,HttpMessageNotReadableException ex) {
		resp.setStatus(HttpStatus.BAD_REQUEST.value());		
		return new ErrorResponse("1002",ex.getMessage());
	}
	

}

class ErrorResponse {
	 String code;
	String message;
	
	

	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}