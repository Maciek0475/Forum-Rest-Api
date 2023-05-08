package com.mac2work.forumrestapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.repository.ThreadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	private final ThreadRepository threadRepository;
	
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

}
