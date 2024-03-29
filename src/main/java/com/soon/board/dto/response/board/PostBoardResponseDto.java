package com.soon.board.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class PostBoardResponseDto extends ResponseDto{

	public PostBoardResponseDto() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}
	
	public static ResponseEntity<PostBoardResponseDto> success() {
		PostBoardResponseDto result = new PostBoardResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> noExistUser() {
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
