package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Message;
import com.mac2work.forumrestapi.repository.MessageRepository;
import com.mac2work.forumrestapi.response.MessageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
	private final MessageRepository messageRepository;
	public List<MessageResponse> getThreadMessages(Integer id) {
		List<Message> messages = messageRepository.findAllByThreadId(id).orElseThrow(
				() -> new ResourceNotFoundException("Message", "thread_id", id));
		return messages.stream().map(
				message -> new MessageResponse(
						message.getThread().getName(),
						message.getUser().getFirstName(),
						message.getContent())).toList();
	}

}
