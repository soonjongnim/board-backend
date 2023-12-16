package com.soon.board.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soon.board.dto.request.auth.SignInRequestDto;
import com.soon.board.dto.request.auth.SignUpRequestDto;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.auth.SignInResponseDto;
import com.soon.board.dto.response.auth.SignUpResponseDto;
import com.soon.board.entity.UserEntity;
import com.soon.board.provider.TokenProvider;
import com.soon.board.repository.UserRepository;
import com.soon.board.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired UserRepository userRepository;
	@Autowired TokenProvider tokenProvider;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
		
		try {
			String email = dto.getEmail();
			boolean existedEmail = userRepository.existsByEmail(email);
			if (existedEmail) return SignUpResponseDto.duplicateEmail();
			String nickname = dto.getNickname();
			boolean existedNickname = userRepository.existsByNickname(nickname);
			if (existedNickname) return SignUpResponseDto.duplicateNickname();
			String telNumber = dto.getTelNumber();
			boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
			if (existedTelNumber) return SignUpResponseDto.duplicateTelNumber();
			
			String password = dto.getPassword();
			// 비밀번호 암호화
			String encodedPassword = passwordEncoder.encode(password);
			dto.setPassword(encodedPassword);
			
			// UserEntity 생성
			UserEntity userEntity = new UserEntity(dto);
			
			// UserRepository를 이용해서 데이터베이스에 Entity 저장!!
			userRepository.save(userEntity);
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return SignUpResponseDto.success();
	}

	@Override
	public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
		
		String token = null;
		
		try {
			String email = dto.getEmail();
			String provider = dto.getProvider();
			if (provider.equals("NAVER") || provider.equals("KAKAO") || provider.equals("GOOGLE")) {
				UserEntity userEntity = userRepository.findByEmailAndProvider(email, provider);
				if (userEntity == null) {
					System.out.println("userEntity == null");
					token = "";
					return SignInResponseDto.notDuplicateEmail();
				} else {
					System.out.println("userEntity != null");
					token = tokenProvider.create(email);
				}
				
			} else {
				UserEntity userEntity = userRepository.findByEmail(email);
				if (userEntity == null) return SignInResponseDto.signInFail();
				
				String password = dto.getPassword();
				String encodedPassword = userEntity.getPassword();
				boolean isMatched = passwordEncoder.matches(password, encodedPassword);
				if (!isMatched) return SignInResponseDto.signInFail();

				token = tokenProvider.create(email);
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return SignInResponseDto.success(token);
	}
	
	
	
//	public ResponseDto<?> signUp(SignUpDto dto) {
//		String email = dto.getEmail();
//		String password = dto.getPassword();
//		String passwordCheck = dto.getPasswordCheck();
//
//		// email 중복 확인
//		try {
//			if (userRepository.existsById(email))
//				return ResponseDto.setFailed("Existed Email!");
//		} catch (Exception e) {
//			return ResponseDto.setFailed("Data Base Error!");
//		}
//		
//		// 비밀번호가 서로 다르면 failed response 반환
//		if (!password.equals(passwordCheck))
//			return ResponseDto.setFailed("Password does not matched!");
//		
//		// UserEntity 생성
//		UserEntity userEntity = new UserEntity(dto);
//		
//		// 비밀번호 암호화
//		String encodedPassword = passwordEncoder.encode(password);
//		userEntity.setPassword(encodedPassword);
//		
//		// UserRepository를 이용해서 데이터베이스에 Entity 저장!!
//		try {
//			userRepository.save(userEntity);
//		} catch (Exception e) {
//			return ResponseDto.setFailed("Data Base Error!");
//		}
//		
//		// 성공시 success response 반환
//		return ResponseDto.setSuccess("Sign Up Success!", null);
//	}
//	
//	public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {
//		String email = dto.getEmail();
//		String password = dto.getPassword();
//		
//		UserEntity userEntity = null;
//		try {
//			userEntity = userRepository.findByUserEmail(email);
//			// 잘못된 이메일
//			if (userEntity == null) return ResponseDto.setFailed("Sign In Failed");
//			// 잘못된 패스워드
//			if (!passwordEncoder.matches(password, userEntity.getPassword())) 
//				return ResponseDto.setFailed("Sign In Failed");
//		} catch (Exception error) {
//			return ResponseDto.setFailed("Database Error");
//		}
//		
//		userEntity.setPassword("");
//		
//		String token = tokenProvider.create(email);
//		int exprTime = 3600000;
//		
//		SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, userEntity);
//		return ResponseDto.setSuccess("sign In Success", signInResponseDto);
//	}
}
