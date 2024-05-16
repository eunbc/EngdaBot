package com.ebcho.engdabot.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.enums.MessageConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

	private static final String START_COMMAND = "/start";
	private static final String TURN_OFF_ALARM_COMMAND = "알람끄기";
	private final ChatGptExecutor chatGptExecutor;
	private final TelegramMessenger telegramMessenger;
	private final TelegramUserReader telegramUserReader;

	public void handleMessage(MessageRequest messageRequest) {
		var user = telegramUserReader.read(messageRequest);
		telegramMessenger.receive(user, messageRequest.message());

		String messageText = messageRequest.message();
		switch (messageText) {
			case START_COMMAND:
				try {
					// 2초간 대기
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // 현재 스레드의 인터럽트 상태를 설정
					log.error("Thread was interrupted", e);
				}
				telegramMessenger.send(user, MessageConstants.START_MESSAGE);
				break;
			case TURN_OFF_ALARM_COMMAND:
				user.turnOffAlarm();
				telegramMessenger.send(user, MessageConstants.TURN_OFF_MESSAGE);
				break;
			default:
				String correctedMessage = chatGptExecutor.correct(messageText);
				telegramMessenger.send(user, correctedMessage);
				break;
		}
	}

	@Scheduled(cron = "0 0 23 * * *")
	public void sendDailyNotification() {
		List<TelegramUser> users = telegramUserReader.getByAlarmTypeOn();
		for (TelegramUser user : users) {
			telegramMessenger.send(user, MessageConstants.DAILY_NOTIFICATION);
		}
	}
}
