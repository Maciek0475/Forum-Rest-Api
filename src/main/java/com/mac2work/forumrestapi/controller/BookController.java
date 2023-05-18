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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.request.BookRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.BookResponse;
import com.mac2work.forumrestapi.response.ThreadResponse;
import com.mac2work.forumrestapi.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
	
	private final BookService bookService;

	@GetMapping
	public ResponseEntity<List<BookResponse>> getBooks(){
		List<BookResponse> books = bookService.getBooks();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getSpecificBook(@PathVariable Integer id){
		BookResponse bookResponse = bookService.getSpecificBook(id);
		
		return new ResponseEntity<>(bookResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/threads")
	public ResponseEntity<List<ThreadResponse>> getSpecificBookThreads(@PathVariable Integer id){
		List<ThreadResponse> threads = bookService.getSpecificBookThreads(id);
		return new ResponseEntity<>(threads, HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest){
		BookResponse bookResponse = bookService.addBook(bookRequest);
		return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BookResponse> updateBook(@RequestBody BookRequest bookRequest, @PathVariable Integer id){
		BookResponse bookResponse = bookService.updateBook(id, bookRequest);
		return new ResponseEntity<>(bookResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteBook(@PathVariable Integer id) {
		ApiResponse apiResponse = bookService.deleteBook(id);
		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
		
	}
}