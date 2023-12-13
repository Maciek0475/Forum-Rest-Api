package com.mac2work.forumrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.mac2work.forumrestapi.service.BookService;

import jakarta.validation.Valid;
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
	
	@PostMapping
	@PreAuthorize("@authorizationService.isAdmin('/books', 'POST')")
	public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest bookRequest){
		BookResponse bookResponse = bookService.addBook(bookRequest);
		return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
	
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("@authorizationService.isAdmin('/books', 'PUT')")
	public ResponseEntity<BookResponse> updateBook(@Valid @RequestBody BookRequest bookRequest, @PathVariable Integer id){
		BookResponse bookResponse = bookService.updateBook(id, bookRequest);
		return new ResponseEntity<>(bookResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("@authorizationService.isAdmin('/books', 'DELETE')")
	public ResponseEntity<ApiResponse> deleteBook(@PathVariable Integer id) {
		ApiResponse apiResponse = bookService.deleteBook(id);
		return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
		
	}
}