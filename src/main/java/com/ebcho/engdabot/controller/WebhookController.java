package com.ebcho.engdabot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebcho.engdabot.service.MessageService;
import com.ebcho.engdabot.telegram.Update;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WebhookController {
	private final MessageService messageService;

	@PostMapping("/update")
	public void getUpdate(@RequestBody Update update) {
		System.out.println("update : " + update);
		// if (update.hasMessage()) {
		// 	messageService.readMessageAndCorrectText(messageRequest);
		// }
	}

}
