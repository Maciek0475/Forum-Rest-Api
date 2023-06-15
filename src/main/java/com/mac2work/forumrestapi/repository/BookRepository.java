package com.mac2work.forumrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac2work.forumrestapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{


}
