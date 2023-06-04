package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.response.UserResponse;
import com.mac2work.forumrestapi.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getUsers(){
		List<UserResponse> users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getSpecificUser(@PathVariable Integer id){
		UserResponse userResponse = userService.getSpecificUser(id);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
}
