package com.ebcho.engdabot.enums;

public final class MessageConstants {
	public static final String START_MESSAGE = """
		<strong>EngdaBot 잉다봇 : 영어일기 첨삭 봇</strong>
		        
		1. 24시간 언제든 영어로 일기를 작성하면 첨삭을 받을 수 있어요
		2. 텔레그램 계정만 있다면 누구나 무료로 이용할 수 있어요
		3. 매일 밤 11시 일기 작성 알람을 받아요
		4. 알람을 끄고 싶다면 <u><b>알람끄기</b></u>를 입력해주세요 (알람 시간 변경 추후 개선 예정)
		        
		자세한 안내 & 문의는
		<a href="https://rena-developedia.notion.site/EngdaBot-ed047b389f4c43e08705ebe39e2e1360?pvs=4">잉다봇 안내 페이지</a> 을 참고해주세요.
		""";
	public static final String DAILY_NOTIFICATION = "How was your day? Write it in your English diary.";
	public static final String TURN_OFF_MESSAGE = "Done! Your alarm is turned off.";

	private MessageConstants() {
	}
}
