package com.soon.board.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.object.BoardListItem;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.entity.BoardListViewEntity;

import lombok.Getter;

@Getter
public class GetLatestBoardListResponseDto extends ResponseDto {

	private List<BoardListItem> latestList;
	
	private GetLatestBoardListResponseDto(List<BoardListViewEntity> boardListViewEntities) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.latestList = BoardListItem.getList(boardListViewEntities);
	}
	
	public static ResponseEntity<GetLatestBoardListResponseDto> success(List<BoardListViewEntity> boardListViewEntities) {
		GetLatestBoardListResponseDto result = new GetLatestBoardListResponseDto(boardListViewEntities);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
