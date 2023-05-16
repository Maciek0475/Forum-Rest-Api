package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.repository.ThreadRepository;
import com.mac2work.forumrestapi.response.ThreadResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreadService {

	private final ThreadRepository threadRepository;
	
	public List<Thread> getThreads() {
		List<Thread> threads = threadRepository.findAll();
		return threads;
	}

}
