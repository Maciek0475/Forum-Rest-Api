package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.UserRepository;
import com.mac2work.forumrestapi.request.UserRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	private User getUser(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	}

	public User getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (User) auth.getPrincipal();
	}

	public List<UserResponse> getUsers() {
		List<User> users = userRepository.findAll();
		
		List<UserResponse> usersResponses = users.stream().map(
				user -> mapToUserResponse(user)).toList();
		
		return usersResponses;
	}

	public UserResponse getSpecificUser(Integer id) {
		User user = getUser(id);
		
		return mapToUserResponse(user);
	}

	public UserResponse updateUser(Integer id, UserRequest userRequest) {
		User user = getUser(id);
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setRole(userRequest.getRole());

		userRepository.save(user);
		User updatedUser = getUser(id);
		
		return mapToUserResponse(updatedUser);
	}

	public ApiResponse deleteUser(Integer id) {
		User user = getUser(id);
		userRepository.delete(user);
		
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("User deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}
	
	public UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.role(user.getRole())
				.build();
	}

}
