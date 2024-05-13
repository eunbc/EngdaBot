package com.ebcho.engdabot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.enums.AlarmType;
import com.ebcho.engdabot.repository.TelegramUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TelegramUserReader {

	private final TelegramUserRepository telegramUserRepository;

	public TelegramUser read(MessageRequest messageRequest) {
		Optional<TelegramUser> optionalUser = telegramUserRepository.findById(messageRequest.chatId());
		return optionalUser.orElseGet(() -> createTelegramUser(messageRequest));
	}

	public List<TelegramUser> getByAlarmTypeOn() {
		return telegramUserRepository.findByAlarmType(AlarmType.ON);
	}

	private TelegramUser createTelegramUser(MessageRequest messageRequest) {
		return telegramUserRepository.save(new TelegramUser(messageRequest.chatId(), messageRequest.firstName()));
	}
}
