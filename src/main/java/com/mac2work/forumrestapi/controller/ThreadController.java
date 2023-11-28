package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.request.ThreadRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.ThreadResponse;
import com.mac2work.forumrestapi.service.ThreadService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ThreadController {
	private final ThreadService threadService;
	
	@GetMapping("/threads")
	public ResponseEntity<List<ThreadResponse>> getThreads(){
		List<ThreadResponse> threads = threadService.getThreads();
		return new ResponseEntity<>(threads, HttpStatus.OK);		
		
	}
	
	@GetMapping("/threads/{id}")
	public ResponseEntity<ThreadResponse> getSpecificThread(@PathVariable Integer id){
		ThreadResponse threadResponse = threadService.getSpecificThread(id);
		return new ResponseEntity<>(threadResponse, HttpStatus.OK);
	}
	
	@GetMapping("/books/{id}/threads")
	public ResponseEntity<List<ThreadResponse>> getSpecificBookThreads(@PathVariable Integer id){
		List<ThreadResponse> threads = threadService.getSpecificBookThreads(id);
		return new ResponseEntity<>(threads, HttpStatus.OK);
	}
	
	@PostMapping("/threads")
	public ResponseEntity<ThreadResponse> addThread(@Valid @RequestBody ThreadRequest threadRequest){
		ThreadResponse threadResponse = threadService.addThread(threadRequest);
		return new ResponseEntity<>(threadResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/threads/{id}")
	public ResponseEntity<ThreadResponse> updateThread(@Valid @RequestBody ThreadRequest threadRequest, @PathVariable Integer id){
		ThreadResponse threadResponse = threadService.updateThread(id, threadRequest);
		return new ResponseEntity<>(threadResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/threads/{id}")
	public ResponseEntity<ApiResponse> deleteThread(@PathVariable Integer id){
		ApiResponse apiResponse = threadService.deleteThread(id);
		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
	}

}
