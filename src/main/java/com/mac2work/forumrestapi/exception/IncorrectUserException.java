package com.mac2work.forumrestapi.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class IncorrectUserException extends RuntimeException {
	
	private static final long serialVersionUID = 4350976516690002972L;
	
	private String resource;
	private String message;
	
	public IncorrectUserException(String resource) {
		this.resource = resource;
		formatMessage();
	}
	
	private void formatMessage() {
		message = String.format("You have no rights to modify this %s", resource);
	}

}
