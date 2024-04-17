package com.ebcho.engdabot.service;

import org.springframework.stereotype.Service;

import com.ebcho.engdabot.dto.MessageRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final ChatGptService chatGptService;

	public Boolean readMessageAndCorrectText(MessageRequest messageRequest) {
		String correctedMessage = chatGptService.correctEnglishText(messageRequest.message());
		System.out.println(correctedMessage);
		// telegramService.sendResponse(messageRequest.chatId(), correctedMessage);
		return true;
	}
}
