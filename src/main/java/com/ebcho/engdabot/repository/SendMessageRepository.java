package com.ebcho.engdabot.repository;

import org.springframework.data.repository.CrudRepository;

import com.ebcho.engdabot.entity.SendMessage;

public interface SendMessageRepository extends CrudRepository<SendMessage, Integer> {
}
