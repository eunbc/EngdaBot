package com.ebcho.engdabot.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TelegramUser {

	@Id
	private Integer id;
	private String firstName;
	private LocalDateTime createdAt;

	public TelegramUser(Integer id, String firstName) {
		this.id = id;
		this.firstName = firstName;
		this.createdAt = LocalDateTime.now();
	}
}
