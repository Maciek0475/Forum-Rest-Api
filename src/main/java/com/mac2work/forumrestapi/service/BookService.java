package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.request.BookRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.BookResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	
	private BookResponse mapToBookResponse(Book book) {
		return BookResponse.builder()
				.name(book.getName())
				.publication_year(book.getPublicationYear())
				.description(book.getDescription())
				.build();
	}
	
	public List<BookResponse> getBooks() {
		return bookRepository.findAll().stream()
				.map(book -> mapToBookResponse(book)).toList();
	}

	public BookResponse getSpecificBook(Integer id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
		return mapToBookResponse(book);
		}

	public BookResponse addBook(BookRequest bookRequest) {
		Book book = Book.builder()
				.name(bookRequest.getName())
				.publicationYear(bookRequest.getPublicationYear())
				.description(bookRequest.getDescription())
				.build();
		bookRepository.save(book);
		
		return mapToBookResponse(book);
	}

	public BookResponse updateBook(Integer id, BookRequest bookRequest) {
		
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

		book.setName(bookRequest.getName());
		book.setPublicationYear(bookRequest.getPublicationYear());
		book.setDescription(bookRequest.getDescription());		
		bookRepository.save(book);		
				
		return mapToBookResponse(book);
	}

	public ApiResponse deleteBook(Integer id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
		bookRepository.delete(book);
		
		return ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("Book deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
				
	}

}
