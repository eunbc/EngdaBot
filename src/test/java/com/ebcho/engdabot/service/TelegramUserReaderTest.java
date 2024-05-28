package com.ebcho.engdabot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.enums.AlarmType;
import com.ebcho.engdabot.repository.TelegramUserRepository;

@ExtendWith(MockitoExtension.class)
class TelegramUserReaderTest {

	@Mock
	private TelegramUserRepository telegramUserRepository;

	@InjectMocks
	private TelegramUserReader telegramUserReader;

	@Test
	@DisplayName("이미 존재하는 유저를 조회합니다")
	void getTelegramUser_ExistingUser() {
		// Arrange
		Long chatId = 1L;
		TelegramUser existingUser = new TelegramUser(chatId, "John");
		when(telegramUserRepository.findById(chatId)).thenReturn(Optional.of(existingUser));

		// Act
		TelegramUser result = telegramUserReader.read(new MessageRequest(chatId, "John", "Hello"));

		// Assert
		assertEquals(existingUser, result);
		verify(telegramUserRepository, never()).save(any(TelegramUser.class));
	}

	@Test
	@DisplayName("새로운 유저를 저장합니다")
	void getTelegramUser_NewUser() {
		// Arrange
		Long chatId = 2L;
		String firstName = "Jane";
		when(telegramUserRepository.findById(chatId)).thenReturn(Optional.empty());
		when(telegramUserRepository.save(any(TelegramUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		TelegramUser result = telegramUserReader.read(new MessageRequest(chatId, firstName, "Hello"));

		// Assert
		assertNotNull(result);
		assertEquals(firstName, result.getFirstName());
		verify(telegramUserRepository).save(any(TelegramUser.class));
	}

	@Test
	@DisplayName("알람타입이 ON인 유저 목록을 조회합니다")
	void getByAlarmTypeOn() {
		// Arrange
		TelegramUser user = new TelegramUser(1L, "John");
		List<TelegramUser> expectedUsers = Collections.singletonList(user);
		when(telegramUserRepository.findByAlarmType(AlarmType.ON)).thenReturn(expectedUsers);

		// Act
		List<TelegramUser> result = telegramUserReader.getByAlarmTypeOn();

		// Assert
		assertEquals(expectedUsers, result);
		assertFalse(result.isEmpty());
	}
}

