package com.ebcho.engdabot.service;

import java.util.Optional;

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
		TelegramUser user = getTelegramUser(messageRequest);

		telegramService.saveReceiveMessage(user, messageRequest.message());
		if (messageRequest.message().equals("/start")) {
			telegramService.sendStartMessage(user);
			return true;
		}

		// chat gpt로 첨삭
		String correctedMessage = chatGptService.correctEnglishText(messageRequest.message());

		// 첨삭 내용을 텔레그램 메시지로 답장
		telegramService.sendResponse(user, correctedMessage);
		return true;
	}

	private TelegramUser getTelegramUser(MessageRequest messageRequest) {
		Optional<TelegramUser> optionalUser = telegramUserRepository.findById(messageRequest.chatId());
		TelegramUser user;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		} else {
			user = telegramUserRepository.save(new TelegramUser(messageRequest.chatId(), messageRequest.firstName()));
		}
		return user;
	}
}
