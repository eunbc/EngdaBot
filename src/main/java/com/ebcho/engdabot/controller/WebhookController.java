package com.ebcho.engdabot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.dto.RegisterUserRequest;
import com.ebcho.engdabot.service.MessageService;
import com.ebcho.engdabot.service.UserService;
import com.ebcho.engdabot.telegram.Update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/update")
public class WebhookController {
	private final MessageService messageService;
	private final UserService userService;

	@PostMapping
	public void getUpdate(@RequestBody Update update) {
		try {
			if (update.hasMessage()) {
				messageService.readMessageAndCorrectText(MessageRequest.from(update));
			} else if (update.hasMyChatMember()) {
				userService.registerUser(RegisterUserRequest.from(update));
			}
		} catch (Exception e) {
			log.error("getUpdate error : ", e);
		}
	}

}
