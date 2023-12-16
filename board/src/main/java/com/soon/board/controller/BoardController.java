package com.soon.board.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soon.board.common.dto.SearchDto;
import com.soon.board.common.paging.PagingResponse;
import com.soon.board.dto.ResponseDto;
import com.soon.board.dto.request.board.PostBoardRequestDto;
import com.soon.board.dto.response.board.PostBoardResponseDto;
import com.soon.board.entity.BoardEntity;
import com.soon.board.service.BoardService;
import com.soon.board.service.BoardServiceTest;

@RestController
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired BoardService boardService;
	@Autowired BoardServiceTest boardServiceTest;
	
	@PostMapping("")
	public ResponseEntity<? super PostBoardResponseDto> postBoard(
			@RequestBody @Valid PostBoardRequestDto requestBody,
			@AuthenticationPrincipal String email) {
		ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, email);
		System.out.println("response: " + response);
		return response;
	}

	@GetMapping("/search/{title}")
	public ResponseDto<List<BoardEntity>> getSearchList(@PathVariable("title") String title) {
		return null;
	};
	
	// 게시글 리스트 페이지(paging처리)
    @GetMapping("/listPaging")
    public PagingResponse<BoardEntity> listPaging(@ModelAttribute("params") final SearchDto params) {
        PagingResponse<BoardEntity> response = boardServiceTest.findAllOfPage(params);
        return response;
    }

}
