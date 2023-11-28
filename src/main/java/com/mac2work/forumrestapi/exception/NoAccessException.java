package com.mac2work.forumrestapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoAccessException extends RuntimeException {

	private static final long serialVersionUID = -3209850499858335595L;
	
	private String path;
	private String mappingMethod;
	private String requiredRole;
	private String message;
	
	public NoAccessException(String path, String mappingMethod, String requiredRole) {
		this.path = path;
		this.mappingMethod = mappingMethod;
		this.requiredRole = requiredRole;
		formatMessage();
	}
	private void formatMessage() {

		this.message = String.format("You have no privileges to access: '%s' through %s mapping, required privilege is: '%s'", path, mappingMethod, requiredRole);
	}
	
	
	
	
}
