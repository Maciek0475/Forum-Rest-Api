package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.request.BookRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.BookResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

	public Book getSpecificBook(Integer id) {
		return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
	}

	public List<Thread> getSpecificBookThreads(Integer id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
		return book.getThreads();
	}

	public BookResponse addBook(BookRequest bookRequest) {
		Book book = Book.builder()
				.name(bookRequest.getName())
				.publicationYear(bookRequest.getPublicationYear())
				.description(bookRequest.getDescription())
				.build();
		bookRepository.save(book);
		
		return BookResponse.builder()
				.id(book.getId())
				.name(book.getName())
				.publication_year(book.getPublicationYear())
				.description(book.getDescription())
				.build();
	}

	public BookResponse updateBook(Integer id, BookRequest bookRequest) {
		
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

		book.setName(bookRequest.getName());
		book.setPublicationYear(bookRequest.getPublicationYear());
		book.setDescription(bookRequest.getDescription());

		System.out.println(book);
		
		bookRepository.save(book);		
		
		Book updatedBook = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
		
		return BookResponse.builder()
				.id(updatedBook.getId())
				.name(updatedBook.getName())
				.publication_year(updatedBook.getPublicationYear())
				.description(updatedBook.getDescription())
				.build();
	}

	public ApiResponse deleteBook(Integer id) {
		bookRepository.deleteById(id);
		
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("Book deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
				
	}

}
