package com.ebcho.engdabot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.service.MessageService;
import com.ebcho.engdabot.telegram.Update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/update")
public class WebhookController {
	private final MessageService messageService;

	@PostMapping
	public void getUpdate(@RequestBody Update update) {
		long startTime = System.currentTimeMillis(); // 시작 시간 측정

		log.info("getUpdate : {}", update);
		try {
			if (update.hasMessage()) {
				messageService.handleMessage(MessageRequest.from(update));
			} else {
				log.info("Update does not have text.");
			}
		} finally {
			long endTime = System.currentTimeMillis(); // 종료 시간 측정
			long duration = endTime - startTime; // 실행 시간 계산
			log.info("Request processed in {} ms", duration);
		}
	}
}
