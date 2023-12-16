package com.soon.board.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.soon.board.entity.primaryKey.FavoritePk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="favorite")
@Table(name="favorite")
@IdClass(FavoritePk.class)	// 복합키 설정
public class FavoriteEntity {
	@Id
	private String userEmail;
	@Id
	private int boardNumber;
}
