package com.ebcho.engdabot.entity;

import java.time.LocalDateTime;

import com.ebcho.engdabot.enums.AlarmType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "telegram_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TelegramUser {

	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Enumerated(EnumType.STRING)
	@Column(name = "alarm_type", nullable = false)
	private AlarmType alarmType = AlarmType.ON;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	public TelegramUser(Long id, String firstName) {
		this.id = id;
		this.firstName = firstName;
		this.createdAt = LocalDateTime.now();
	}
}
