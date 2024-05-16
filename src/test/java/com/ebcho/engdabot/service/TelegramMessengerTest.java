package com.ebcho.engdabot.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebcho.engdabot.entity.ReceiveMessage;
import com.ebcho.engdabot.entity.SendMessage;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.repository.ReceiveMessageRepository;
import com.ebcho.engdabot.repository.SendMessageRepository;
import com.ebcho.engdabot.telegram.SendMessageRequest;
import com.ebcho.engdabot.telegram.TelegramClient;

class TelegramMessengerTest {

	@Mock
	private TelegramClient telegramClient;

	@Mock
	private SendMessageRepository sendMessageRepository;

	@Mock
	private ReceiveMessageRepository receiveMessageRepository;

	@InjectMocks
	private TelegramMessenger telegramMessenger;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("메시지 전송 성공 후 보낸 메시지를 저장합니다")
	void shouldSaveSendMessageOnSuccess() {
		// Given
		TelegramUser user = new TelegramUser(1L, "John");
		String message = "Hello, World!";

		// When
		telegramMessenger.send(user, message);

		// Then
		verify(telegramClient).sendMessage(new SendMessageRequest(user.getId(), message, "HTML"));
		verify(sendMessageRepository).save(any(SendMessage.class));
	}

	@Test
	@DisplayName("메시지 전송 실패시 전송 기록을 저장합니다")
	void shouldSaveSendMessageOnError() {
		// Given
		TelegramUser user = new TelegramUser(1L, "John");
		String message = "Hello, World!";
		doThrow(new RuntimeException("Failed to send message")).when(telegramClient)
			.sendMessage(any(SendMessageRequest.class));

		// When
		try {
			telegramMessenger.send(user, message);
		} catch (Exception ignored) {
			// Exception handling for test purposes
		}

		// Then
		verify(sendMessageRepository).save(any(SendMessage.class));
	}

	@Test
	@DisplayName("받은 메시지를 저장합니다")
	void shouldSaveReceivedMessage() {
		// Given
		TelegramUser user = new TelegramUser(1L, "John");
		String receivedMessage = "Received message";

		// When
		telegramMessenger.receive(user, receivedMessage);

		// Then
		verify(receiveMessageRepository).save(any(ReceiveMessage.class));
	}
}
