package com.ebcho.engdabot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebcho.engdabot.entity.Diary;
import com.ebcho.engdabot.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

	private final DiaryRepository diaryRepository;

	public List<Diary> getDiarysBytelegramId(Long id) {
		return diaryRepository.findByTelegramUserId(id);
	}
}
