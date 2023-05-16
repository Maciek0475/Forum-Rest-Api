package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.request.BookRequest;
import com.mac2work.forumrestapi.response.BookResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

	public Book getSpecificBook(Integer id) {
		return bookRepository.findById(id).orElseThrow();
	}

	public List<Thread> getSpecificBookThreads(Integer id) {
		Book book = bookRepository.findById(id).orElseThrow();
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

		Book book = Book.builder()
				.name(bookRequest.getName())
				.publicationYear(bookRequest.getPublicationYear())
				.description(bookRequest.getDescription())
				.build();
		
		bookRepository.updateById(id, book);
		
		Book updatedBook = bookRepository.findById(id).orElseThrow();
		
		return BookResponse.builder()
				.id(book.getId())
				.name(book.getName())
				.publication_year(book.getPublicationYear())
				.description(book.getDescription())
				.build();
	}

}
