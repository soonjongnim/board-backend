package com.soon.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fruit {
	private String email;
	private String password;
	private String nickname;
	private String telNumber;
	private String address;
	private String addressDetail;
	private Boolean agreedPersonal;

}
