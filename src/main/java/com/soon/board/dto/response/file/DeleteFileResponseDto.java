package com.soon.board.dto.response.file;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class DeleteFileResponseDto extends ResponseDto {

	private DeleteFileResponseDto() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}
	
	public static ResponseEntity<DeleteFileResponseDto> success() {
		DeleteFileResponseDto result = new DeleteFileResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> notExistUser() {
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	public static ResponseEntity<ResponseDto> notExistImages() {
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_IMAGES, ResponseMessage.NOT_EXISTED_IMAGES);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
