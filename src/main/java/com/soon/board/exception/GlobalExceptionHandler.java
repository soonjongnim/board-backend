package com.soon.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.entity.PaymentsInfoEntity;

import lombok.Getter;

@Getter
public class GlobalExceptionHandler extends ResponseDto {
	
	private PaymentsInfoEntity paymentsInfoEntity;
	
	private GlobalExceptionHandler(PaymentsInfoEntity paymentsInfoEntity) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.paymentsInfoEntity = paymentsInfoEntity;
	}

	/**
	 * 게시글작성시 모든값이 제대로 입력되었을때
	 * @return
	 */
	public static ResponseEntity<ResponseDto> success(PaymentsInfoEntity paymentsInfoEntity){
		GlobalExceptionHandler result = new GlobalExceptionHandler(paymentsInfoEntity);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	/**
	 * 게시글작성시 모든값이 제대로 입력되지 않았을때 발생하는 예외
	 * @return
	 */
	public ResponseEntity<ResponseDto> dataIntegrityViolationError(){
		ResponseDto responseBody = new ResponseDto("DIVE", "값이 제대로 입력되지 않았습니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	/**
	 * 실제 결제금액과 DB의 결제 금액이 다를때 발생하는 예외
	 * @return
	 */
	public ResponseEntity<ResponseDto> verifyIamportException(){
		ResponseDto responseBody = new ResponseDto("VE", "실제 결제금액과 서버에서 결제금액이 다릅니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	/**
	 * 환불가능 금액과 아임포트 서버에서 조회한 결제했던 금액이 일치하지 않을때 발생하는 예외
	 * @return
	 */
	public ResponseEntity<ResponseDto> refundAmountIsDifferent(){
		ResponseDto responseBody = new ResponseDto("RAE", "환불가능 금액과 결제했던 금액이 일치하지 않습니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
}
