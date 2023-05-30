package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.model.Message;
import com.mac2work.forumrestapi.response.MessageResponse;
import com.mac2work.forumrestapi.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;
 //get/add/update/delete User messages
	// get/add/update/delete Thread messages
	
	@GetMapping("threads/{id}/messages")
	public ResponseEntity<List<MessageResponse>> getThreadMessages(@PathVariable Integer id){
		// List<Message> messages = messageService.getThreadMessages(threadController.getSpecificThread(id).getBody());
		List<MessageResponse> messages = messageService.getThreadMessages(id);
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
}
