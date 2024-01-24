package com.ebcho.engdabot;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Diary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(name = "diary_text")
	String diaryText;
	@Column(name = "corected_text")
	String correctedText;
	LocalDateTime createdAt;
	Long memberId;
}
