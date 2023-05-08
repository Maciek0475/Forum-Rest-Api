package com.mac2work.forumrestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Integer>{

	List<Thread> findByBookId(Integer id);

}
