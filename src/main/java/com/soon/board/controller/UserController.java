package com.soon.board.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soon.board.dto.Fruit;
import com.soon.board.dto.request.user.PatchNicknameRequestDto;
import com.soon.board.dto.request.user.PatchProfileImageRequestDto;
import com.soon.board.dto.response.user.GetSignInUserResponseDto;
import com.soon.board.dto.response.user.GetUserResponseDto;
import com.soon.board.dto.response.user.PatchNicknameResponseDto;
import com.soon.board.dto.response.user.PatchProfileImageResponseDto;
import com.soon.board.service.FruitService;
import com.soon.board.service.UserService;

//@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired UserService userService;
	@Autowired FruitService fruitService;
	
	@GetMapping("/{email}")
	public ResponseEntity<? super GetUserResponseDto> getUser(
			@PathVariable("email") String email) {
		ResponseEntity<? super GetUserResponseDto> response = userService.getUser(email);
		return response;
	}
	
	@GetMapping("")
	public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
			@AuthenticationPrincipal String email) {
		System.out.println("getSignInUser");
		ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(email);
		System.out.println("response: " + response.toString());
		return response;
	};
	
	@GetMapping("/fruit")
	public List<Fruit> getFruitList() {
		System.out.println("getFruitList");
		System.out.println(fruitService.getList());
		return fruitService.getList();
	};
	
	@PatchMapping("/nickname")
	public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(
			@RequestBody @Valid PatchNicknameRequestDto requestBody,
			@AuthenticationPrincipal String email) {
		ResponseEntity<? super PatchNicknameResponseDto> response = userService.patchNickname(requestBody, email);
		return response;
	};
	
	@PatchMapping("/profile-image")
	public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(
			@RequestBody @Validated PatchProfileImageRequestDto requestBody,
			@AuthenticationPrincipal String email) {
		ResponseEntity<? super PatchProfileImageResponseDto> response = userService.patchProfileImage(requestBody, email);
		return response;
	}

}
