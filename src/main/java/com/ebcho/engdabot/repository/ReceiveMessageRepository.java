package com.ebcho.engdabot.repository;

import org.springframework.data.repository.CrudRepository;

import com.ebcho.engdabot.entity.ReceiveMessage;

public interface ReceiveMessageRepository
	extends CrudRepository<ReceiveMessage, Long>, ReceiveMessageQueryDslRepository {
}
