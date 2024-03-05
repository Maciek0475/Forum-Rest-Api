package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.repository.ThreadRepository;
import com.mac2work.forumrestapi.request.ThreadRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.ThreadResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreadService {

	private final ThreadRepository threadRepository;
	private final BookRepository bookRepository;
	
	private final UserService userService;
	private final AuthorizationService authorizationService;
	private final BookService bookService;
	
	private Book getBook(ThreadRequest threadRequest) {
		Book book = bookRepository.findById(threadRequest.getBookId()).orElseThrow(
				() -> new ResourceNotFoundException("Book", "id", threadRequest.getBookId()));
		return book;
	}
	
	private ThreadResponse mapToThreadResponse(Thread thread) {
		return ThreadResponse.builder()
				.name(thread.getName())
				.book(bookService.mapToBookResponse(thread.getBook()))
				.user(userService.mapToUserResponse(thread.getUser()))
				.content(thread.getContent())
				.build();
	}
	
	private Thread mapToThread(ThreadRequest threadRequest, Book book, User user) {
		return Thread.builder()
				.name(threadRequest.getName())
				.book(book)
				.user(user)
				.content(threadRequest.getContent())
				.build();
	}
	
	public List<ThreadResponse> getThreads() {
		List<Thread> threads = threadRepository.findAll();
		
		return threads.stream().map(
				thread -> mapToThreadResponse(thread)).toList();
		
	}

	public ThreadResponse getSpecificThread(Integer id) {
		Thread thread = getThread(id);
		
		return mapToThreadResponse(thread);
	}
	
	public List<ThreadResponse> getSpecificBookThreads(Integer id) {
		List<ThreadResponse> threads = threadRepository.findAllByBookId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Threads", "bookId", id)).stream()
				.map(thread -> mapToThreadResponse(thread)).toList();
		
		return threads;
	}

	public ThreadResponse addThread(ThreadRequest threadRequest) {
		Book book = getBook(threadRequest);
		User user = userService.getLoggedInUser();
		
		Thread thread = mapToThread(threadRequest, book, user);
		
		threadRepository.save(thread);
		
		return mapToThreadResponse(thread);
	}

	public ThreadResponse updateThread(Integer id, ThreadRequest threadRequest) {
		Thread thread = getThread(id);
		authorizationService.isCorrectUser(thread.getUser().getId(), "thread");
		Book book = getBook(threadRequest);
		
		thread.setName(threadRequest.getName());
		thread.setBook(book);
		thread.setContent(threadRequest.getContent());
		
		thread = threadRepository.save(thread);
		return mapToThreadResponse(thread);
	}
	
	public ApiResponse deleteThread(Integer id) {
		Thread thread = getThread(id);
		authorizationService.isCorrectUser(thread.getUser().getId(), "thread");
		
		threadRepository.delete(getThread(id));
		
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("Thread deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	public Thread getThread(Integer id) {
		Thread thread = threadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Thread", "id", id));
		return thread;
	}
	

}
