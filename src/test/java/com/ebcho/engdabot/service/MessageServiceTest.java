package com.ebcho.engdabot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.enums.AlarmType;
import com.ebcho.engdabot.enums.MessageConstants;

class MessageServiceTest {

	@Mock
	private ChatGptExecutor chatGptExecutor;

	@Mock
	private TelegramMessenger telegramMessenger;

	@Mock
	private TelegramUserReader telegramUserReader;

	@InjectMocks
	private MessageService messageService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("/start 메시지 입력시 시작 메시지를 답장한다")
	void handleMessage_whenStartCommand() {
		// Arrange
		MessageRequest messageRequest = new MessageRequest(1L, "John", "/start");
		TelegramUser user = new TelegramUser(1L, "John");

		when(telegramUserReader.read(messageRequest)).thenReturn(user);

		// Act
		messageService.handleMessage(messageRequest);

		// Assert
		verify(telegramMessenger).send(user, MessageConstants.START_MESSAGE);
	}

	@Test
	@DisplayName("알림끄기 메시지 입력시 사용자 알람을 끕니다")
	void handleMessage_whenTurnOffAlarmCommand() {
		// Arrange
		MessageRequest messageRequest = new MessageRequest(1L, "John", "알람끄기");
		TelegramUser user = new TelegramUser(1L, "John");

		when(telegramUserReader.read(messageRequest)).thenReturn(user);

		// Act
		messageService.handleMessage(messageRequest);

		// Assert
		assertEquals(AlarmType.OFF, user.getAlarmType());
		verify(telegramMessenger).send(user, MessageConstants.TURN_OFF_MESSAGE);
	}

	@Test
	@DisplayName("default로는 문장을 첨삭 후 답장합니다")
	void handleMessage_defaultCase() {
		// Arrange
		String originalMessage = "Hello";
		String correctedMessage = "Hello, World!";
		MessageRequest messageRequest = new MessageRequest(1L, "John", originalMessage);
		TelegramUser user = new TelegramUser(1L, "John");

		when(telegramUserReader.read(messageRequest)).thenReturn(user);
		when(chatGptExecutor.correct(originalMessage)).thenReturn(correctedMessage);

		// Act
		messageService.handleMessage(messageRequest);

		// Assert
		verify(telegramMessenger).send(user, correctedMessage);
	}

	@Test
	@DisplayName("일일 알림 메시지를 보냅니다")
	void sendDailyNotification_sendsMessages() {
		// Arrange
		TelegramUser user1 = new TelegramUser(1L, "John");
		TelegramUser user2 = new TelegramUser(2L, "Jane");
		List<TelegramUser> users = Arrays.asList(user1, user2);

		when(telegramUserReader.getByAlarmTypeOn()).thenReturn(users);

		// Act
		messageService.sendDailyNotification();

		// Assert
		verify(telegramMessenger).send(user1, MessageConstants.DAILY_NOTIFICATION);
		verify(telegramMessenger).send(user2, MessageConstants.DAILY_NOTIFICATION);
	}
}
