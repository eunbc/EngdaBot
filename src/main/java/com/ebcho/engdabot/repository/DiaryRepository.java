package com.ebcho.engdabot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ebcho.engdabot.entity.Diary;

public interface DiaryRepository extends CrudRepository<Diary, Long> {
	List<Diary> findByTelegramUserId(Long telegramUserId);
}
