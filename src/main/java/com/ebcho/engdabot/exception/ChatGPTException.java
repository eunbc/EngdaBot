package com.ebcho.engdabot.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ChatGPTException extends RuntimeException {
	private final String code;
	private final HttpStatus status;

	public ChatGPTException(ErrorResponse errorResponse, HttpStatus status) {
		super(errorResponse.getErrorMessage());
		this.code = errorResponse.getErrorCode();
		this.status = status;
	}
}
