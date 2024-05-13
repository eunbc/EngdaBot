package com.ebcho.engdabot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebcho.engdabot.entity.ReceiveMessage;
import com.ebcho.engdabot.entity.SendMessage;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.repository.ReceiveMessageRepository;
import com.ebcho.engdabot.repository.SendMessageRepository;
import com.ebcho.engdabot.telegram.SendMessageRequest;
import com.ebcho.engdabot.telegram.TelegramClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TelegramMessenger {

	private final TelegramClient telegramClient;
	private final SendMessageRepository sendMessageRepository;
	private final ReceiveMessageRepository receiveMessageRepository;

	public void send(TelegramUser user, String responseText) {
		sendMessage(user, responseText);
	}

	public void receive(TelegramUser user, String message) {
		receiveMessageRepository.save(new ReceiveMessage(message, user));
	}

	private void sendMessage(TelegramUser user, String startMessage) {
		try {
			telegramClient.sendMessage(new SendMessageRequest(user.getId(), startMessage));
		} catch (Exception e) {
			log.error("sendMessage error : ", e);
			sendMessageRepository.save(new SendMessage(startMessage, user, e.getMessage()));
			return;
		}
		sendMessageRepository.save(new SendMessage(startMessage, user, null));
	}
}
