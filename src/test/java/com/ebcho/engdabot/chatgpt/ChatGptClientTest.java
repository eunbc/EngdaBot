// package com.ebcho.engdabot.chatgpt;
//
// import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
// import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.client.MockRestServiceServer;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @RestClientTest(ChatGptClient.class)
// class ChatGptClientTest {
//
// 	@Autowired
// 	MockRestServiceServer server;
//
// 	@Autowired
// 	ChatGptClient chatGptClient;
//
// 	@Autowired
// 	ObjectMapper objectMapper;
//
// 	@Test
// 	void chatCompletions() throws JsonProcessingException {
// 		// given
// 		ChatCompletionResponse response = new ChatCompletionResponse();
//
// 		// when
// 		server.expect(requestTo("https://api.openai.com/v1/chat/completions"))
// 			.andRespond(withSuccess(objectMapper.writeValueAsString(response), MediaType.APPLICATION_JSON));
//
// 		// then
// 		var result = chatGptClient.chatCompletions(ChatCompletionRequest.requestBuilder("hi!"));
// 		System.out.println(result);
// 	}
// }
