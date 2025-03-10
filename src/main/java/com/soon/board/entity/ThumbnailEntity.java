package com.soon.board.entity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

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
@Entity(name="thumbnail_images")
@Table(name="thumbnail_images")
public class ThumbnailEntity {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Integer itemId;
	private int sequence;
	private String thumbnailUrl;
	private String createdAt;
	
	public ThumbnailEntity(int itemId, String image, int seq) {
		System.out.println("itemId: " + itemId);
		if (itemId > 0) {
			Date now = Date.from(Instant.now());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String writeDatetime = simpleDateFormat.format(now);
			
			this.itemId = itemId;
			this.sequence = seq;
			this.thumbnailUrl = image;
			this.createdAt = writeDatetime;
        } else {
        	throw new IllegalArgumentException("Error: ThumbnailEntity itemId is null or invalid.");
        }
	}
}
