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

	public void sendResponse(Integer chatId, String responseText) {
		telegramClient.sendMessage(new SendMessageRequest(chatId.toString(), responseText));
	}

	public void sendStartMessage(Integer chatId) {
		String startMessage = """
			Welcome to EngdaBot!
						
			1. 영어 일기를 작성하고 첨삭을 받을 수 있어요.
			2. 매일 밤 11시 일기 작성 알람이 울려요. 
			3. 알람을 원치 않는다면 '알람X'를 입력해주세요.
						
			자세한 안내 및 문의는
			http://notion.com 을 참고해주세요.
			""";

	}
}
