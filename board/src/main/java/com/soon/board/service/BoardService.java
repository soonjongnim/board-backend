package com.soon.board.service;

import org.springframework.http.ResponseEntity;

import com.soon.board.dto.request.board.PostBoardRequestDto;
import com.soon.board.dto.response.board.PostBoardResponseDto;

public interface BoardService {
	ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
}
