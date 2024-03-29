package com.soon.board.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.response.ResponseDto;

public class IncreaseViewCountResponseDto extends ResponseDto {
	
	public IncreaseViewCountResponseDto() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}
	
	public static ResponseEntity<IncreaseViewCountResponseDto> success() {
		IncreaseViewCountResponseDto result = new IncreaseViewCountResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> notExistUser() {
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
