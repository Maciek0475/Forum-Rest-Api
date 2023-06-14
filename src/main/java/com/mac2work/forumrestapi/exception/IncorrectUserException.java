package com.mac2work.forumrestapi.exception;

import lombok.Data;

@Data
public class IncorrectUserException extends RuntimeException {
	
	private String resource;
	private String message;
	
	public IncorrectUserException(String resource) {
		this.resource = resource;
		formatMessage();
	}
	
	private void formatMessage() {
		this.message = String.format("You have no rights to modify this %s", resource);
	}

}
