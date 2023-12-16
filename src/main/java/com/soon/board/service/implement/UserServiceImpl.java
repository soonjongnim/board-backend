package com.soon.board.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.user.GetSignInUserResponseDto;
import com.soon.board.entity.UserEntity;
import com.soon.board.repository.UserRepository;
import com.soon.board.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired UserRepository userRepository;
	
	@Override
	public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
		
		UserEntity userEntity = null;
		
		try {
			userEntity = userRepository.findByEmail(email);
			if (userEntity == null) return GetSignInUserResponseDto.notExistUser();
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetSignInUserResponseDto.success(userEntity);
	}
	
}
