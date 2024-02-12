package com.ebcho.engdabot.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "diary_text")
	private String diaryText;
	@Column(name = "corrected_text")
	private String correctedText;
	private Long telegramUserId;
	private LocalDateTime createdAt;

	public Diary(String diaryText, String correctedText, Long telegramUserId) {
		this.diaryText = diaryText;
		this.correctedText = correctedText;
		this.telegramUserId = telegramUserId;
		this.createdAt = LocalDateTime.now();
	}
}
