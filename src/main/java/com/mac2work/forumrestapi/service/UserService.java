package com.mac2work.forumrestapi.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
public User getLoggedInUser() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getPrincipal().toString();
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException("User", "email", email));
		return user;
	}

}
