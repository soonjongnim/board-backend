package com.soon.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soon.board.dto.Fruit;
import com.soon.board.dto.response.user.GetSignInUserResponseDto;
import com.soon.board.service.FruitService;
import com.soon.board.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired UserService userService;
	@Autowired FruitService fruitService;
	
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

}
