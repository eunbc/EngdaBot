package com.ebcho.engdabot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ebcho.engdabot.service.UpdatePollingService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EngdaBotRunner implements CommandLineRunner {

	private final UpdatePollingService updatePollingService;

	@Value("${telegram.bot-token}")
	private String botToken;

	@Override
	public void run(String... args) throws Exception {
		updatePollingService.startPolling(botToken);
	}
}
