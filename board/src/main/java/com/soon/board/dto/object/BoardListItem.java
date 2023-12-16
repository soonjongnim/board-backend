package com.soon.board.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListItem {

	private int boardNumber;
	private String title;
	private String content;
	private String boardTitleImage;
	private String favoriteCount;
	private String commentCount;
	private String viewCount;
	private String writeDatetime;
	private String writerNickname;
	private String writerProfileImage;
}
