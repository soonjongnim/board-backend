package com.soon.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(originPatterns = "http://localhost:3000")	// Ư������Ʈ ��������
@RestController	// �ش� Ŭ������ Controller���̾�� �ν��ϵ��� ��, Rest�� ����(@Controller + @ResponseBody)�� ����
@RequestMapping("/") // request�� URL�� ������ ���� �ش��ϴ� ������ ���� �� �ش� Ŭ������ ����
public class MainController {
	
	@GetMapping("")
	public String hello() {
		return "Connection Successful";
	}
}
