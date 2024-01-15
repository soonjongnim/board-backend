package com.soon.board.entity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.soon.board.dto.request.board.PatchBoardRequestDto;
import com.soon.board.dto.request.board.PostBoardRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "board") // 해당 클래스를 Entity클래스로 사용.
@Table(name = "board") // 데이터베이스에 해당 테이블과 현재 클래스를 매핑 시키겠다.
public class BoardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT (기본키 생성)
	private int boardNumber;
	private String title;
	private String content;
	private String writeDatetime;
	private int favoriteCount;
	private int commentCount;
	private int viewCount;
	private String writerEmail;
	
	public BoardEntity(PostBoardRequestDto dto, String email) {
		
		Date now = Date.from(Instant.now());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String writeDatetime = simpleDateFormat.format(now);
		
		this.title = dto.getTitle();
		this.content = dto.getContent();
		this.writeDatetime = writeDatetime;
		this.favoriteCount = 0;
		this.commentCount = 0;
		this.viewCount = 0;
		this.writerEmail = email;
	}
	
	public void increaseViewcount() {
		this.viewCount++;
	}
	
	public void increaseCommentCount() {
		this.commentCount++;
	}
	
	public void increaseFavoriteCount() {
		this.favoriteCount++;
	}
	
	public void decreaseFavoriteCount() {
		this.favoriteCount--;
	}
	
	public void patchBoard(PatchBoardRequestDto dto) {
		this.title = dto.getTitle();
		this.content = dto.getContent();
	}
}
