package com.ebcho.engdabot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebcho.engdabot.dto.ApiResponse;
import com.ebcho.engdabot.dto.PageResponse;
import com.ebcho.engdabot.dto.ReceiveMessageResponse;
import com.ebcho.engdabot.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
	private final AdminService adminService;

	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<ReceiveMessageResponse>>> getQuestions(
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "10") int size,
		@RequestParam(value = "query", required = false) String query
	) {
		PageResponse<ReceiveMessageResponse> response = adminService.getReceiveMessages(page, size, query);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}
}
