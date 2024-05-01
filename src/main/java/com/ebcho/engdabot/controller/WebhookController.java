package com.ebcho.engdabot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.service.MessageService;
import com.ebcho.engdabot.telegram.Update;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/update")
public class WebhookController {
	private final MessageService messageService;

	@PostMapping
	public void getUpdate(@RequestBody Update update) {
		if (update.hasMessage()) {
			messageService.readMessageAndCorrectText(MessageRequest.from(update));
		}
	}

}
