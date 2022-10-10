package com.epicode.undercontrol.errors;

public class UserExceptionNotFound extends UserException {
	
	public UserExceptionNotFound(String message) {
		super(message);
	}
	
	public UserExceptionNotFound(String message, Throwable cause) {
		super(message, cause);
	}

}
