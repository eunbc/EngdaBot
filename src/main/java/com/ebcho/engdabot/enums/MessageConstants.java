package com.ebcho.engdabot.enums;

public final class MessageConstants {
	public static final String START_MESSAGE = """
		Welcome to EngdaBot!
		        
		1. 영어 일기를 작성하고 첨삭을 받을 수 있어요.
		2. 매일 밤 11시 일기 작성 알람이 울려요. 
		3. 알람을 원치 않는다면 '알람끄기' 를 입력해주세요.
		        
		자세한 안내 및 문의는
		http://notion.com 을 참고해주세요.
		""";
	public static final String DAILY_NOTIFICATION = "How was your day? Write it in your English diary.";
	public static final String TURN_OFF_MESSAGE = "Done! Your alarm is turned off.";

	private MessageConstants() {
	}
}
