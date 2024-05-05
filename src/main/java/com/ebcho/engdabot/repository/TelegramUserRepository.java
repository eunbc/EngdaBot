package com.ebcho.engdabot.repository;

import org.springframework.data.repository.CrudRepository;

import com.ebcho.engdabot.entity.TelegramUser;

public interface TelegramUserRepository extends CrudRepository<TelegramUser, Integer> {
}
