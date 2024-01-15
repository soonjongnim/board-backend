package com.soon.board.service;

import org.springframework.http.ResponseEntity;

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

public interface BoardService {
	ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
	ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
	ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);
	ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList();
	ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList();
	ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord);
	ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email);
	ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
	ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);
	ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);
	ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email);
	ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);
	ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email);
}
