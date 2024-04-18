package com.ebcho.engdabot.service;

import org.springframework.stereotype.Service;

import com.ebcho.engdabot.telegram.SendMessageRequest;
import com.ebcho.engdabot.telegram.TelegramClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService {

	private final TelegramClient telegramClient;

	public void sendResponse(Long chatId, String responseText) {
		telegramClient.sendMessage(new SendMessageRequest(chatId.toString(), responseText));
	}

}
