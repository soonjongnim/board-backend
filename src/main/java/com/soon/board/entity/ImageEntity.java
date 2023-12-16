package com.soon.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="image")
@Table(name="image")
public class ImageEntity {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sequence;
	private int boardNumber;
	private String image;
	
	public ImageEntity(int boardNumber, String image) {
		this.boardNumber = boardNumber;
		this.image = image;
	}
}
