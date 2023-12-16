package com.soon.board.entity.primaryKey;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritePk implements Serializable {
	@Column(name="user_email")
	private String userEmail;
	@Column(name="board_number")
	private int boardNumber;
}
