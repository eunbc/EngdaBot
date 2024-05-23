package com.ebcho.engdabot.dto;

import java.time.LocalDateTime;

import com.ebcho.engdabot.entity.ReceiveMessage;

public record ReceiveMessageResponse(
	Long id,
	String content,
	Long senderId,
	LocalDateTime receivedAt
) {
	public static ReceiveMessageResponse from(ReceiveMessage receiveMessage) {
		return new ReceiveMessageResponse(
			receiveMessage.getId(),
			receiveMessage.getContent(),
			receiveMessage.getSender().getId(),
			receiveMessage.getReceivedAt()
		);
	}
}
