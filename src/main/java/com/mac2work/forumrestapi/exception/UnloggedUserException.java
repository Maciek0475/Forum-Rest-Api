package com.mac2work.forumrestapi.exception;

import lombok.Data;

@Data
public class UnloggedUserException extends RuntimeException{

	private String message;
	
	public UnloggedUserException() {
		formatMessage();
	}
	
	private void formatMessage() {
		this.message = String.format("You have to log in first");
	}
}
