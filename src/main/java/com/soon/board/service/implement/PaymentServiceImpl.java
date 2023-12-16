package com.soon.board.service.implement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.payment.PaymentResponseDto;
import com.soon.board.entity.PaymentsInfoEntity;
import com.soon.board.exception.GlobalExceptionHandler;
import com.soon.board.repository.BoardRepository;
import com.soon.board.repository.PaymentRepository;
import com.soon.board.repository.UserRepository;
import com.soon.board.service.PaymentService;

/**
 * 결제 관련 서비스를 제공해주는 로직
 * @author USER
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired UserRepository userRepository;
	@Autowired BoardRepository boardRepository;
	@Autowired PaymentRepository paymentRepository;
	
	/**
	 * 은행이름에 따른 코드들을 반환해줌<br>
	 * KG이니시스 기준.
	 * @param bankName
	 * @return
	 */
	@Override
	public String code(String bankName) {
		String code="";
		if(bankName.equals("우리은행")||bankName.equals("우리")) code="20";
		else if(bankName.equals("국민은행")||bankName.equals("국민")) code="04";
		return code;
	}

	/**
	 * 현재 결제번호에 해당하는 정보를 갖고와서 반환해줌.
	 * @param paymentsNo
	 * @return
	 */
	@Transactional
	@Override
	public PaymentsInfoEntity paymentLookup(long paymentsNo) {
		PaymentsInfoEntity paymentsInfo = paymentRepository.getById(paymentsNo);
		return paymentsInfo;
	}

	/**
	 * 아임포트 서버쪽 결제내역과 DB에 물건가격을 비교하는 서비스. <br>
	 * 다름 -> 예외 발생시키고 GlobalExceptionHandler쪽에서 예외처리 <br>
	 * 같음 -> 결제정보를 DB에 저장(PaymentsInfo 테이블)
	 * @param irsp (아임포트쪽 결제 내역 조회 정보)
	 * @param actionBoardNo (내 DB에서 물건가격 알기위한 경매게시글 번호)
	 * @throws verifyIamportException
	 */
	@Transactional
	@Override
	public ResponseEntity<ResponseDto> verifyIamport(IamportResponse<Payment> irsp, String userEmail) throws IamportResponseException {
		LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current Date and Time: " + currentDateTime);
		PaymentsInfoEntity paymentsInfo = PaymentsInfoEntity.builder()
				.pgProvider(irsp.getResponse().getPgProvider())
				.payMethod(irsp.getResponse().getPayMethod())
				.impUid(irsp.getResponse().getImpUid())
				.merchantUid(irsp.getResponse().getMerchantUid())
				.productName(irsp.getResponse().getName())
				.amount(irsp.getResponse().getAmount().intValue())
				.pgTid(irsp.getResponse().getPgTid())
				.buyerName(irsp.getResponse().getBuyerName())
				.buyerEmail(irsp.getResponse().getBuyerEmail())
				.buyerTel(irsp.getResponse().getBuyerTel())
				.buyerAddr(irsp.getResponse().getBuyerAddr())
				.buyerPostcode(irsp.getResponse().getBuyerPostcode())
				.buyDatetime(currentDateTime)
				.paidAt(irsp.getResponse().getPaidAt().toString())
				.returnCode(irsp.getCode())
				.returnMsg(irsp.getMessage())
				.status(irsp.getResponse().getStatus())
				.userEmail(userEmail)
				.build();
		
		paymentRepository.save(paymentsInfo);
		
		return GlobalExceptionHandler.success(paymentsInfo);
		
	}

	/**
	 * 결제 취소할때 필요한 파라미터들을
	 * CancelData에 셋업해주고 반환함.
	 * @param map
	 * @param impUid
	 * @param bankAccount
	 * @param code
	 * @return
	 * @throws RefundAmountIsDifferent 
	 */
	@Transactional
	@Override
	public ResponseEntity<? super PaymentResponseDto> cancelData(Map<String, String> map, IamportResponse<Payment> lookUp) {
		//아임포트 서버에서 조회된 결제금액 != 환불(취소)될 금액 이면 예외발생
		if(lookUp.getResponse().getAmount()!=new BigDecimal(map.get("checksum"))) 
			return PaymentResponseDto.notSameAmount();
		
		CancelData data = new CancelData(lookUp.getResponse().getImpUid(),true);
		data.setReason(map.get("reason"));
		data.setChecksum(new BigDecimal(map.get("checksum")));
		data.setRefund_holder(map.get("refundHolder"));
//		data.setRefund_bank(code);
//		data.setRefund_account(principal.getBankName());
		return PaymentResponseDto.success(map);
	}

}
