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
public class TelegramService {

	private final TelegramClient telegramClient;
	private final SendMessageRepository sendMessageRepository;
	private final ReceiveMessageRepository receiveMessageRepository;

	public void sendResponse(TelegramUser user, String responseText) {
		sendMessage(user, responseText);
	}

	public void sendStartMessage(TelegramUser user) {
		String startMessage = """
			Welcome to EngdaBot!
						
			1. 영어 일기를 작성하고 첨삭을 받을 수 있어요.
			2. 매일 밤 11시 일기 작성 알람이 울려요. 
			3. 알람을 원치 않는다면 **알람끄기** 를 입력해주세요.
						
			자세한 안내 및 문의는
			http://notion.com 을 참고해주세요.
			""";
		sendMessage(user, startMessage);
	}

	public void saveReceiveMessage(TelegramUser user, String message) {
		receiveMessageRepository.save(new ReceiveMessage(message, user));
	}

	private void sendMessage(TelegramUser user, String startMessage) {
		try {
			telegramClient.sendMessage(new SendMessageRequest(user.getId(), startMessage));
		} catch (Exception e) {
			sendMessageRepository.save(new SendMessage(startMessage, user, e.getMessage()));
			return;
		}
		sendMessageRepository.save(new SendMessage(startMessage, user, null));
	}
}
