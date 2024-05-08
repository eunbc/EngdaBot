package com.ebcho.engdabot.entity;

import java.time.LocalDateTime;

import com.ebcho.engdabot.enums.SendMessageStatus;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "send_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendMessage {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private SendMessageStatus status;

	@Nullable
	@Column(name = "fail_message")
	private String failMessage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TelegramUser receiver;

	@Column(name = "sent_at", nullable = false)
	private LocalDateTime sentAt;
}
