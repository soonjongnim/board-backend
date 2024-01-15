package com.soon.board.entity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.soon.board.dto.request.board.PostCommentRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="comment") // 해당 클래스를 Entity클래스로 사용.
@Table(name="comment") // 데이터베이스에 해당 테이블과 현재 클래스를 매핑 시키겠다.
public class CommentEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentNumber;
	private String content;
	private String writeDatetime;
	private String userEmail;
	private int boardNumber;
	
	public CommentEntity(PostCommentRequestDto dto, Integer boardNumber, String email) {
		
		Date now = Date.from(Instant.now());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String writeDatetime = simpleDateFormat.format(now);
		
		this.content = dto.getContent();
		this.writeDatetime = writeDatetime;
		this.userEmail = email;
		this.boardNumber = boardNumber;
	}
}
