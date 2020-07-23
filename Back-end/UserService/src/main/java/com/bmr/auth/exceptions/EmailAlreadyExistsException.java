package com.bmr.auth.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8939849002820733406L;

	public EmailAlreadyExistsException(String message) {
		super(message);
	}

}
