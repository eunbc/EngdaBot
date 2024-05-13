package com.ebcho.engdabot.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ebcho.engdabot.enums.AlarmType;

class TelegramUserTest {

	@Test
	@DisplayName("텔레그램 유저 생성자")
	void testTelegramUserConstructor() {
		// Arrange
		Long expectedId = 1L;
		String expectedFirstName = "John";

		// Act
		TelegramUser user = new TelegramUser(expectedId, expectedFirstName);

		// Assert
		assertNotNull(user.getCreatedAt(), "createdAt should not be null");
		assertEquals(expectedId, user.getId(), "ID should match the expected ID");
		assertEquals(expectedFirstName, user.getFirstName(), "First name should match the expected first name");
		assertEquals(AlarmType.ON, user.getAlarmType(), "Alarm type should default to ON");
	}

	@Test
	@DisplayName("알람을 끕니다")
	void testTurnOffAlarm() {
		// Arrange
		TelegramUser user = new TelegramUser(1L, "John");

		// Act
		user.turnOffAlarm();

		// Assert
		assertEquals(AlarmType.OFF, user.getAlarmType(), "Alarm type should be turned off");
	}
}
