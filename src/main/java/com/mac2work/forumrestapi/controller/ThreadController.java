package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.request.ThreadRequest;
import com.mac2work.forumrestapi.response.MessageResponse;
import com.mac2work.forumrestapi.response.ThreadResponse;
import com.mac2work.forumrestapi.service.ThreadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/threads")
public class ThreadController {
	private final ThreadService threadService;
	
	@GetMapping
	public ResponseEntity<List<ThreadResponse>> getThreads(){
		List<ThreadResponse> threads = threadService.getThreads();
		return new ResponseEntity<>(threads, HttpStatus.OK);		
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ThreadResponse> getSpecificThread(@PathVariable Integer id){
		ThreadResponse threadResponse = threadService.getSpecificThread(id);
		return new ResponseEntity<>(threadResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/messages")
	public ResponseEntity<List<MessageResponse>> getSpecificThreadMessages(@PathVariable Integer id){
		List<MessageResponse> messageResponses = threadService.getSpecificThreadMessages(id);
		return new ResponseEntity<>(messageResponses, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ThreadResponse> addThread(@RequestBody ThreadRequest threadRequest){
		ThreadResponse threadResponse = threadService.addThread(threadRequest);
		return new ResponseEntity<>(threadResponse, HttpStatus.CREATED);
	}
	

}
