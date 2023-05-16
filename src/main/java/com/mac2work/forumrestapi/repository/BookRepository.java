package com.mac2work.forumrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mac2work.forumrestapi.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{


}
