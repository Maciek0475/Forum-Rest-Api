package com.mac2work.forumrestapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac2work.forumrestapi.model.Thread;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Integer>{

	Optional<List<Thread>> findAllByBookId(Integer id);

}
