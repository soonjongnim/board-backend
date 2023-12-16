package com.soon.board.dto.request.auth;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInRequestDto {
	@NotBlank
	private String email;
	private String password;
	private String provider;

}
