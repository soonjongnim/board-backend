package com.soon.board.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.soon.board.dto.request.auth.SignUpRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="user") // 해당 클래스를 Entity클래스로 사용.
@Table(name="user") // 데이터베이스에 해당 테이블과 현재 클래스를 매핑 시키겠다.
public class UserEntity {
	
	@Id
	private String email;
	private String username;
	private String password;
	private String nickname;
	private String telNumber;
	private String address;
	private String addressDetail;
	private String profileImage;
	private Boolean agreedPersonal;
	private String provider;
	
	public UserEntity(SignUpRequestDto dto) {
		this.email = dto.getEmail();
		this.username = dto.getUsername();
		this.password = dto.getPassword();
		this.nickname = dto.getNickname();
		this.telNumber = dto.getTelNumber();
		this.address = dto.getAddress();
		this.addressDetail = dto.getAddressDetail();
		this.profileImage = dto.getProfileImage();
		this.agreedPersonal = dto.getAgreedPersonal();
		this.provider = dto.getProvider();
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
}
