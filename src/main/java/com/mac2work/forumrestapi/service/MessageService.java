package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Message;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.MessageRepository;
import com.mac2work.forumrestapi.request.MessageRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.MessageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
	private final ThreadService threadService;
	private final UserService userService;
	private final AuthorizationService authorizationService;
	
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
	
	public MessageResponse addThreadMessage(MessageRequest messageRequest, Integer id) {
		User loggedInUser = userService.getLoggedInUser();
		Thread thread = threadService.getThread(id);
		String content = messageRequest.getContent();
		messageRepository.save(
				Message.builder()
				.thread(thread)
				.user(loggedInUser)
				.content(content)
				.build());
		
		return MessageResponse.builder()
				.threadName(thread.getName())
				.userName(loggedInUser.getFirstName())
				.content(content)
				.build();
	}
	
	public MessageResponse updateMessage(MessageRequest messageRequest, Integer id) {
		Message message = getMessage(id);
		authorizationService.isCorrectUser(message.getUser().getId(), "message");
		message.setContent(messageRequest.getContent());
		messageRepository.save(message);
		Message updatedMessage = getMessage(id);
		Thread thread = updatedMessage.getThread();
		User user = updatedMessage.getUser();
		String content = updatedMessage.getContent();
		
		return MessageResponse.builder()
				.threadName(thread.getName())
				.userName(user.getFirstName())
				.content(content)
				.build();
	}
	
	private Message getMessage(Integer id) {
		Message message = messageRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Message", "id", id));
		return message;
	}
	
	public ApiResponse deleteMessage(Integer id) {
		Message message = getMessage(id);
		authorizationService.isCorrectUser(message.getUser().getId(), "message");
		messageRepository.deleteById(id);
		
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("Message with id: "+id+" deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

}
