package com.mac2work.forumrestapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac2work.forumrestapi.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	Optional<List<Message>> findAllByThreadId(Integer id);

}
