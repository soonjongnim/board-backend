package com.soon.board.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soon.board.dto.request.user.PatchNicknameRequestDto;
import com.soon.board.dto.request.user.PatchProfileImageRequestDto;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.board.PatchBoardResponseDto;
import com.soon.board.dto.response.user.GetSignInUserResponseDto;
import com.soon.board.dto.response.user.GetUserResponseDto;
import com.soon.board.dto.response.user.PatchNicknameResponseDto;
import com.soon.board.dto.response.user.PatchProfileImageResponseDto;
import com.soon.board.entity.UserEntity;
import com.soon.board.repository.UserRepository;
import com.soon.board.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired UserRepository userRepository;
	
	@Override
	public ResponseEntity<? super GetUserResponseDto> getUser(String email) {
		
		UserEntity userEntity = null;
		
		try {
			
			userEntity = userRepository.findByEmail(email);
			if (userEntity == null) return GetUserResponseDto.notExistUser();
			
		} catch (Exception exceptione) {
			exceptione.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetUserResponseDto.success(userEntity);
	}
	
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

	@Override
	public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {
		
		try {
			UserEntity userEntity = userRepository.findByEmail(email);
			if (userEntity == null) return PatchNicknameResponseDto.notExistUser();
			
			String nickname = dto.getNickname();
			boolean existedNickname = userRepository.existsByNickname(nickname);
			if (existedNickname) return PatchNicknameResponseDto.duplicateNickname();
			
			userEntity.setNickname(nickname);
			userRepository.save(userEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return PatchNicknameResponseDto.success();
	}

	@Override
	public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto,
			String email) {
		try {
			UserEntity userEntity = userRepository.findByEmail(email);
			if (userEntity == null) return PatchProfileImageResponseDto.notExistUser();
			
			String profileImage = dto.getProfileImage();
			
			userEntity.setProfileImage(profileImage);
			userRepository.save(userEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return PatchProfileImageResponseDto.success();
	}
	
}
