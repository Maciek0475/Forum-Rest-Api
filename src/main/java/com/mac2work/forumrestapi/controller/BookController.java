package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.request.BookRequest;
import com.mac2work.forumrestapi.response.BookResponse;
import com.mac2work.forumrestapi.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
	
	private final BookService bookService;

	@GetMapping
	public ResponseEntity<List<Book>> getBooks(){
		return ResponseEntity.ok(bookService.getBooks());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getSpecificBook(@PathVariable Integer id){
		
		return ResponseEntity.ok(bookService.getSpecificBook(id));
	}
	
	@GetMapping("/{id}/threads")
	public ResponseEntity<List<Thread>> getSpecificBookThreads(@PathVariable Integer id){
		
		return ResponseEntity.ok(bookService.getSpecificBookThreads(id));
	}
	
	@PostMapping
	public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest){
	
		return ResponseEntity.ok(bookService.addBook(bookRequest));
	
	}
}