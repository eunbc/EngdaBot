package com.ebcho.engdabot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.enums.AlarmType;
import com.ebcho.engdabot.enums.MessageConstants;
import com.ebcho.engdabot.repository.TelegramUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

	private static final String START_COMMAND = "/start";
	private static final String TURN_OFF_ALARM_COMMAND = "알람끄기";
	private final ChatGptService chatGptService;
	private final TelegramService telegramService;
	private final TelegramUserRepository telegramUserRepository;

	public Boolean readMessageAndCorrectText(MessageRequest messageRequest) {
		TelegramUser user = getTelegramUser(messageRequest);
		String messageText = messageRequest.message();
		telegramService.saveReceiveMessage(user, messageRequest.message());

		switch (messageText) {
			case START_COMMAND:
				handleStartCommand(user);
				break;
			case TURN_OFF_ALARM_COMMAND:
				handleTurnOffAlarmCommand(user);
				break;
			default:
				handleDefault(user, messageText);
				break;
		}
		return true;
	}

	private void handleDefault(TelegramUser user, String messageText) {
		String correctedMessage = chatGptService.correctEnglishText(messageText);
		telegramService.sendResponse(user, correctedMessage);
	}

	private void handleTurnOffAlarmCommand(TelegramUser user) {
		user.turnOffAlarm();
		telegramService.sendResponse(user, MessageConstants.TURN_OFF_MESSAGE);
	}

	private void handleStartCommand(TelegramUser user) {
		telegramService.sendResponse(user, MessageConstants.START_MESSAGE);
	}

	@Scheduled(cron = "0 0 23 * * *")
	public void sendDailyNotification() {
		List<TelegramUser> users = telegramUserRepository.findByAlarmType(AlarmType.ON);
		for (TelegramUser user : users) {
			telegramService.sendResponse(user, MessageConstants.DAILY_NOTIFICATION);
		}
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
