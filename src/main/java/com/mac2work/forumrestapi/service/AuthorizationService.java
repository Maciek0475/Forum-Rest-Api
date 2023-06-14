package com.mac2work.forumrestapi.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.NoAccessException;

@Service
public class AuthorizationService {
	
	public boolean isAdmin(String path, String mappingMethod) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = auth.getAuthorities().stream()
			.filter(a -> a.toString() == "ADMIN")
			.findAny().orElseThrow(
					() -> new NoAccessException(path, mappingMethod, "ADMIN")) != null;
		
		if(isAdmin)
			return true;
		else
			return false;
	}

}
