package com.soon.board.dto.request.auth;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
	
	@NotBlank @Email
	private String email;
	@NotBlank
	private String username;
	@NotBlank @Size(min=8, max=20)
	private String password;
	@NotBlank
	private String nickname;
	@NotBlank @Pattern(regexp = "^[0-9]{11,13}$")
	private String telNumber;
	@NotBlank
	private String address;
	private String addressDetail;
	private String profileImage;
	@NotNull @AssertTrue
	private Boolean agreedPersonal;
	private String provider;

}
