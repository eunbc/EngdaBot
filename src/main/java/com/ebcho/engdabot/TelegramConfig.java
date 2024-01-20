package com.ebcho.engdabot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfig {
	private String botToken;
	private String chatId;
}
