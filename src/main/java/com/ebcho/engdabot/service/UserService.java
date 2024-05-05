package com.ebcho.engdabot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebcho.engdabot.dto.RegisterUserRequest;
import com.ebcho.engdabot.repository.TelegramUserRepository;
import com.ebcho.engdabot.telegram.ChatMember;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final TelegramUserRepository telegramUserRepository;

	public void registerUser(RegisterUserRequest request) {
		if (request.status().equals(ChatMember.Status.member)) {
			telegramUserRepository.save(request.toEntity());
		} else if (request.status().equals(ChatMember.Status.kicked)) {
			telegramUserRepository.delete(request.toEntity());
		}
	}
}
