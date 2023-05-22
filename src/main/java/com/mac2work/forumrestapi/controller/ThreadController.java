package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.model.Thread;
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
	
	

}
