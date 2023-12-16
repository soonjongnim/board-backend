package com.soon.board.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soon.board.dto.request.auth.SignInRequestDto;
import com.soon.board.dto.request.auth.SignUpRequestDto;
import com.soon.board.dto.response.auth.SignInResponseDto;
import com.soon.board.dto.response.auth.SignUpResponseDto;
import com.soon.board.entity.UserEntity;
import com.soon.board.provider.TokenProvider;
import com.soon.board.repository.UserRepository;
import com.soon.board.service.AuthService;

//@CrossOrigin(originPatterns = "http://localhost:3000")	// 특정사이트 응답해줌
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired AuthService authService;
	@Autowired UserRepository userRepository;
	@Autowired TokenProvider tokenProvider;
	
	@PostMapping("/signUp")
	public ResponseEntity<? super SignUpResponseDto> signUp(
			@RequestBody @Valid SignUpRequestDto requestBody) {
		System.out.println("requsetBody: " + requestBody.toString());
		ResponseEntity<? super SignUpResponseDto> result = authService.signUp(requestBody);
		System.out.println("result: " + result);
		return result;
	}
	
	@PostMapping("/signIn")
	public ResponseEntity<? super SignInResponseDto> signIn(
			@RequestBody @Valid SignInRequestDto requestBody) {
		ResponseEntity<? super SignInResponseDto> result = authService.signIn(requestBody);
		System.out.println("result: " + result);
		return result;
	}

    @PostMapping("/socialOauth")
    public ResponseEntity<? super SignInResponseDto> loginNaver(@RequestBody @Valid Map<String, Object> params) {
    	System.out.println("params: " + params);
    	String provider = (String)params.get("provider");
    	String email = (String)params.get("email");
    	UserEntity userEntity = userRepository.findByEmailAndProvider(email, provider);
    	System.out.println("userEntity: " + userEntity);
		if (userEntity == null) {
			SignUpRequestDto dto = new SignUpRequestDto();
			dto.setEmail(email);
			dto.setUsername((String)params.get("username"));
			dto.setNickname((String)params.get("nickname"));
			dto.setTelNumber((String)params.get("telNumber"));
			dto.setProvider((String)params.get("provider"));
			dto.setAddress((String)params.get("address"));
			dto.setAddressDetail((String)params.get("addressDetail"));
			dto.setPassword("");
			dto.setAgreedPersonal((boolean)params.get("agreedPersonal"));
			// UserEntity 생성
			UserEntity userEntity2 = new UserEntity(dto);
			
			// UserRepository를 이용해서 데이터베이스에 Entity 저장!!
			userRepository.save(userEntity2);
//			return SignInResponseDto.success(token);
		}
		String token = tokenProvider.create(email);
        return SignInResponseDto.success(token);
    }
    
	
//	@PostMapping("/signIn")
//	public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody) {
//		System.out.println("signIn requsetBody: " + requestBody.toString());
//		ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
//		return result;
//	}

}
