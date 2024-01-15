package com.soon.board.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soon.board.common.dto.SearchDto;
import com.soon.board.common.paging.PagingResponse;
import com.soon.board.dto.ResponseDto;
import com.soon.board.dto.request.board.PatchBoardRequestDto;
import com.soon.board.dto.request.board.PostBoardRequestDto;
import com.soon.board.dto.request.board.PostCommentRequestDto;
import com.soon.board.dto.response.board.DeleteBoardResponseDto;
import com.soon.board.dto.response.board.GetBoardResponseDto;
import com.soon.board.dto.response.board.GetCommentListResponseDto;
import com.soon.board.dto.response.board.GetFavoriteListResponseDto;
import com.soon.board.dto.response.board.GetLatestBoardListResponseDto;
import com.soon.board.dto.response.board.GetSearchBoardListResponseDto;
import com.soon.board.dto.response.board.GetTop3BoardListResponseDto;
import com.soon.board.dto.response.board.GetUserBoardListResponseDto;
import com.soon.board.dto.response.board.IncreaseViewCountResponseDto;
import com.soon.board.dto.response.board.PatchBoardResponseDto;
import com.soon.board.dto.response.board.PostBoardResponseDto;
import com.soon.board.dto.response.board.PostCommentResponseDto;
import com.soon.board.dto.response.board.PutFavoriteResponseDto;
import com.soon.board.entity.BoardEntity;
import com.soon.board.service.BoardService;
import com.soon.board.service.BoardServiceTest;

//@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired BoardService boardService;
	@Autowired BoardServiceTest boardServiceTest;
	
	@GetMapping("/{boardNumber}")
	public ResponseEntity<? super GetBoardResponseDto> getBoard(@PathVariable("boardNumber") Integer boardNumber) {
		ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);
		return response;
	}
	
	@GetMapping("/{boardNumber}/favorite-list")
	public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
			@PathVariable("boardNumber") Integer boardNumber) {
		ResponseEntity<? super GetFavoriteListResponseDto> response = boardService.getFavoriteList(boardNumber);
		return response;
	}
	
	@GetMapping("/{boardNumber}/comment-list")
	public ResponseEntity<? super GetCommentListResponseDto> getcommentList(
			@PathVariable("boardNumber") Integer boardNumber) {
		ResponseEntity<? super GetCommentListResponseDto> response = boardService.getCommentList(boardNumber);
		return response;
	}
	
	@GetMapping("/{boardNumber}/increase-view-count")
	public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(
			@PathVariable("boardNumber") Integer boardNumber) {
		ResponseEntity<? super IncreaseViewCountResponseDto> response = boardService.increaseViewCount(boardNumber);
		return response;
	}
	
	@GetMapping("/latest-list")
	public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {
		ResponseEntity<? super GetLatestBoardListResponseDto> response = boardService.getLatestBoardList();
		return response;
	}
	
	@GetMapping("/top-3")
	public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {
		ResponseEntity<? super GetTop3BoardListResponseDto> response = boardService.getTop3BoardList();
		return response;
	}
	
	@GetMapping(value= {"/search-list/{searchWord}", "/search-list/{searchWord}/{preSearchWord}"})
	public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(
			@PathVariable("searchWord") String searchWord,
			@PathVariable(value="preSearchWord", required=false) String preSearchWord) {
		ResponseEntity<? super GetSearchBoardListResponseDto> response = boardService.getSearchBoardList(searchWord, preSearchWord);
		return response;
	}
	
	@GetMapping("/user-board-list/{email}")
	public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(
			@PathVariable("email") String email) {
		ResponseEntity<? super GetUserBoardListResponseDto> response = boardService.getUserBoardList(email);
		return response;
	}
	
	@PostMapping("")
	public ResponseEntity<? super PostBoardResponseDto> postBoard(
			@RequestBody @Valid PostBoardRequestDto requestBody,
			@AuthenticationPrincipal String email) {
		ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, email);
		System.out.println("response: " + response);
		return response;
	}
	
	@PostMapping("/{boardNumber}/comment")
	public ResponseEntity<? super PostCommentResponseDto> postComment(
			@RequestBody @Valid PostCommentRequestDto dto,
			@PathVariable("boardNumber") Integer boardNumber,
			@AuthenticationPrincipal String email) {
		ResponseEntity<? super PostCommentResponseDto> response = boardService.postComment(dto, boardNumber, email);
		return response;
	}
	
	@PutMapping("/{boardNumber}/favorite")
	public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
			@PathVariable("boardNumber") Integer boardNumber, @AuthenticationPrincipal String email) {
		ResponseEntity<? super PutFavoriteResponseDto> response = boardService.putFavorite(boardNumber, email);
		return response;
	}
	
	@PatchMapping("/{boardNumber}")
	public ResponseEntity<? super PatchBoardResponseDto> patchBoard(
			@RequestBody @Valid PatchBoardRequestDto dto,
			@PathVariable("boardNumber") Integer boardNumber,
			@AuthenticationPrincipal String email) {
		ResponseEntity<? super PatchBoardResponseDto> response = boardService.patchBoard(dto, boardNumber, email);
		return response;
	}
	
	@DeleteMapping("/{boardNumber}")
	public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(
			@PathVariable("boardNumber") Integer boardNumber, @AuthenticationPrincipal String email) {
		ResponseEntity<? super DeleteBoardResponseDto> response = boardService.deleteBoard(boardNumber, email);
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
