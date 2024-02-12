package com.ebcho.engdabot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebcho.engdabot.entity.Diary;
import com.ebcho.engdabot.service.DiaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

	private final DiaryService diaryService;

	@GetMapping("/{id}")
	public List<Diary> getDiarysBytelegramId(@PathVariable Long id) {
		return diaryService.getDiarysBytelegramId(id);
	}
}
