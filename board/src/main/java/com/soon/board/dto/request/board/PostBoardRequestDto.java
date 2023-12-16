package com.soon.board.dto.request.board;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostBoardRequestDto {

	@NotBlank
	private String title;
	@NotBlank
	private String content;
	@NotNull
	private List<String> boardImageList;
}
