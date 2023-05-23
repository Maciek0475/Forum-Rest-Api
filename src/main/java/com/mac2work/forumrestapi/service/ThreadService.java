package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.response.MessageResponse;
import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Message;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.repository.ThreadRepository;
import com.mac2work.forumrestapi.response.ThreadResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreadService {

	private final ThreadRepository threadRepository;
	
	public List<ThreadResponse> getThreads() {
		List<Thread> threads = threadRepository.findAll();
		
		return threads.stream().map(
				thread -> new ThreadResponse(
						thread.getName(), 
						thread.getBook(), 
						thread.getUser(), 
						thread.getContent())).toList();
		
	}

	public ThreadResponse getSpecificThread(Integer id) {
		Thread thread = threadRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Thread", "id", id));
		
		return ThreadResponse.builder()
				.name(thread.getName())
				.book(thread.getBook())
				.user(thread.getUser())
				.content(thread.getContent())
				.build();
	}

	public List<MessageResponse> getSpecificThreadMessages(Integer id) {
		Thread thread = threadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Thread", "id", id));
		List<Message> messages = thread.getMessages();
		
		return messages.stream().map(
				message -> new MessageResponse(
						message.getThread(),
						message.getUser(),
						message.getContent())
				).toList();
	}

}
