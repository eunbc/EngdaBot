package com.ebcho.engdabot.bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ebcho.engdabot.service.UpdatePollingService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EngdaBotRunner implements CommandLineRunner {

	private final UpdatePollingService updatePollingService;

	@Override
	public void run(String... args) throws Exception {
		updatePollingService.startPolling();
	}
}
