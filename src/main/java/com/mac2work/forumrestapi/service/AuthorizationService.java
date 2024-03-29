package com.mac2work.forumrestapi.service;

import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.IncorrectUserException;
import com.mac2work.forumrestapi.exception.NoAccessException;
import com.mac2work.forumrestapi.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
	
	private final UserService userService;
	
	private boolean isAdmin(User user) {
		boolean isAdmin = user.getRole().toString() == "ADMIN";
		return isAdmin;
	}
	
	public boolean isAdmin(String path, String mappingMethod) {
		User user = userService.getLoggedInUser();
		boolean isAdmin = isAdmin(user);
		if(!isAdmin)
			throw new NoAccessException(path, mappingMethod, "ADMIN");
		return isAdmin;
	}
	
	public boolean isCorrectUser(Integer id, String resource) {
		
		User user = userService.getLoggedInUser();
		if(isAdmin(user))
		return true;
		boolean isCorrectUser = user.getId() == id;
		if(!isCorrectUser)
			throw new IncorrectUserException(resource);
		return isCorrectUser;

	}

}
