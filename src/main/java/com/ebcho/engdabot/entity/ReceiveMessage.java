package com.ebcho.engdabot.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiveMessage {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "received_at", nullable = false)
	private LocalDateTime receivedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TelegramUser sender;
}
