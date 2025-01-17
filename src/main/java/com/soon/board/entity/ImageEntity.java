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
	private Integer boardNumber;
	private Integer itemId;
	private String image;
	private String type;
	
	public ImageEntity(int number, String image, String type) {
		if ("BOARD".equals(type)) {
			this.boardNumber = number;
			this.image = image;
			this.type = type;
        } else if ("ITEM".equals(type)) {
			this.itemId = number;
			this.image = image;
			this.type = type;
        } else {
        	throw new IllegalArgumentException("Type must be either 'board' or 'item'");
        }
	}
}
