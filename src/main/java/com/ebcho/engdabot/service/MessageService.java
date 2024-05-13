package com.ebcho.engdabot.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.enums.MessageConstants;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

	private static final String START_COMMAND = "/start";
	private static final String TURN_OFF_ALARM_COMMAND = "알람끄기";
	private final ChatGptService chatGptService;
	private final TelegramService telegramService;
	private final TelegramUserService telegramUserService;

	public void handleMessage(MessageRequest messageRequest) {
		TelegramUser user = telegramUserService.getTelegramUser(messageRequest);
		telegramService.saveReceiveMessage(user, messageRequest.message());

		String messageText = messageRequest.message();
		switch (messageText) {
			case START_COMMAND:
				telegramService.sendResponse(user, MessageConstants.START_MESSAGE);
				break;
			case TURN_OFF_ALARM_COMMAND:
				user.turnOffAlarm();
				telegramService.sendResponse(user, MessageConstants.TURN_OFF_MESSAGE);
				break;
			default:
				String correctedMessage = chatGptService.correctEnglishText(messageText);
				telegramService.sendResponse(user, correctedMessage);
				break;
		}
	}

	@Scheduled(cron = "0 0 23 * * *")
	public void sendDailyNotification() {
		List<TelegramUser> users = telegramUserService.getByAlarmTypeOn();
		for (TelegramUser user : users) {
			telegramService.sendResponse(user, MessageConstants.DAILY_NOTIFICATION);
		}
	}
}
