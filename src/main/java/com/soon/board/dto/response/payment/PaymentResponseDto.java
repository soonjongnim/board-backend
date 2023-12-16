package com.soon.board.dto.response.payment;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class PaymentResponseDto extends ResponseDto {

	private String reason;
	private BigDecimal checksum;
	private String refundHolder;
	
	public PaymentResponseDto(Map<String, String> map) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.reason = map.get("reason");
		this.checksum = new BigDecimal(map.get("checksum"));
		this.refundHolder = map.get("refundHolder");
	}
	
	public static ResponseEntity<PaymentResponseDto> success(Map<String, String> map) {
		PaymentResponseDto result = new PaymentResponseDto(map);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> notSameAmount() {
		ResponseDto result = new ResponseDto("NSA", "실제 결제금액과 서버에서 결제금액이 다릅니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
