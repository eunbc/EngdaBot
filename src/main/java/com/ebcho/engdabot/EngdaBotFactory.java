package com.ebcho.engdabot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EngdaBotFactory {

	private final OpenAIConfig openAIConfig;

	@Autowired
	public EngdaBotFactory(OpenAIConfig openAIConfig) {
		this.openAIConfig = openAIConfig;
	}

	public EngdaBot createEngdaBot(String botToken) {
		EngdaBot engdaBot = new EngdaBot(botToken);
		engdaBot.setApiKey(openAIConfig.getApiKey());
		return engdaBot;
	}
}
