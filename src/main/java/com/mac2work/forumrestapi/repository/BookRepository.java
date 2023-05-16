package com.mac2work.forumrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.request.BookRequest;
import com.mac2work.forumrestapi.response.BookResponse;

public interface BookRepository extends JpaRepository<Book, Integer>{

	void updateById(Integer id, Book book);

}
