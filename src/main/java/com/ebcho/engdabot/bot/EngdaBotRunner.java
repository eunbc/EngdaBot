package com.ebcho.engdabot.bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ebcho.engdabot.service.TelegramService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EngdaBotRunner implements CommandLineRunner {

	private final TelegramService telegramService;

	@Override
	public void run(String... args) throws Exception {
		telegramService.startPolling();
	}
}
