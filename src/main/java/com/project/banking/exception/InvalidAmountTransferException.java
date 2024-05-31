package com.project.banking.exception;

public class InvalidAmountTransferException extends AccountException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAmountTransferException(String message) {
		super(message);
	}
}
