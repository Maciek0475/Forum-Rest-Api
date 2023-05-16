package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.service.ThreadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/threads")
public class ThreadController {
	private final ThreadService threadService;
	
	@GetMapping
	public ResponseEntity<List<Thread>> getThreads(){
		return ResponseEntity.ok(threadService.getThreads());
		
	}

}
