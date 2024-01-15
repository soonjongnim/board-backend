package com.soon.board.service;

import org.springframework.http.ResponseEntity;

import com.soon.board.dto.request.user.PatchNicknameRequestDto;
import com.soon.board.dto.request.user.PatchProfileImageRequestDto;
import com.soon.board.dto.response.user.GetSignInUserResponseDto;
import com.soon.board.dto.response.user.GetUserResponseDto;
import com.soon.board.dto.response.user.PatchNicknameResponseDto;
import com.soon.board.dto.response.user.PatchProfileImageResponseDto;

public interface UserService {
	
	ResponseEntity<? super GetUserResponseDto> getUser(String email);
	ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
	ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email);
	ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email);
}
