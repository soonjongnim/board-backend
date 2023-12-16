package com.soon.board.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.payment.PaymentResponseDto;
import com.soon.board.entity.PaymentsInfoEntity;

public interface PaymentService {
	String code(String bankName);
	PaymentsInfoEntity paymentLookup(long paymentsNo);
	ResponseEntity<ResponseDto> verifyIamport(IamportResponse<Payment> irsp, String userEmail) throws IamportResponseException;
	ResponseEntity<? super PaymentResponseDto> cancelData(Map<String,String> map, IamportResponse<Payment> lookUp); 

}
