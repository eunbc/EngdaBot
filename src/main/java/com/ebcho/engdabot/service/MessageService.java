package com.ebcho.engdabot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.repository.TelegramUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

	private final ChatGptService chatGptService;
	private final TelegramService telegramService;
	private final TelegramUserRepository telegramUserRepository;

	public Boolean readMessageAndCorrectText(MessageRequest messageRequest) {
		if (messageRequest.message().equals("/start")) {
			if (!telegramUserRepository.existsById(messageRequest.chatId())) {
				telegramUserRepository.save(new TelegramUser(messageRequest.chatId(), messageRequest.firstName()));
			}

			telegramService.sendStartMessage(messageRequest.chatId());
			return true;
		}

		// chat gpt로 첨삭
		String correctedMessage = chatGptService.correctEnglishText(messageRequest.message());

		// 첨삭 내용을 텔레그램 메시지로 답장
		telegramService.sendResponse(messageRequest.chatId(), correctedMessage);
		return true;
	}
}
