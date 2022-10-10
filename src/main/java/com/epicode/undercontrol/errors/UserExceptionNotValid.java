package com.epicode.undercontrol.errors;

public class UserExceptionNotValid extends UserException {
	
	public UserExceptionNotValid(String message) {
		super(message);
	}
	
	public UserExceptionNotValid(String message, Throwable cause) {
		super(message, cause);
	}

}
