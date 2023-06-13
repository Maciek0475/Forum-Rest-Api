package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.request.UserRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.UserResponse;
import com.mac2work.forumrestapi.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Secured("ADMIN")
public class UserController {

	private final UserService userService;
	
	@GetMapping
	@PreAuthorize("@authorizationService.isAdmin('/users')")
	public ResponseEntity<List<UserResponse>> getUsers(){
		List<UserResponse> users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getSpecificUser(@PathVariable Integer id){
		UserResponse userResponse = userService.getSpecificUser(id);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @Valid @RequestBody UserRequest userRequest){
		UserResponse userResponse = userService.updateUser(id, userRequest);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
		}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
		ApiResponse apiResponse = userService.deleteUser(id);
		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
	}
	
}
