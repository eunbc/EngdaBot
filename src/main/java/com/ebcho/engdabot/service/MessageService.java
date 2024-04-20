package com.ebcho.engdabot.service;

import org.springframework.stereotype.Service;

import com.ebcho.engdabot.dto.MessageRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final ChatGptService chatGptService;
	private final TelegramService telegramService;

	public Boolean readMessageAndCorrectText(MessageRequest messageRequest) {
		// chat gpt로 첨삭
		String correctedMessage = chatGptService.correctEnglishText(messageRequest.message());

		// 첨삭 내용을 텔레그램 메시지로 답장
		telegramService.sendResponse(messageRequest.chatId(), correctedMessage);
		return true;
	}
}
