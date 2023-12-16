package com.soon.board.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.entity.PaymentsInfoEntity;
import com.soon.board.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Value("${iamport.key}")
    private String restApiKey;
    @Value("${iamport.secret}")
    private String restApiSecret;

    @Autowired
    private PaymentService paymentService;
    
    //토큰 발급을 위해 아임포트에서 제공해주는 rest api 사용.(gradle로 의존성 추가)
    private IamportClient iamportClient;

    //생성자로 rest api key와 secret을 입력해서 토큰 바로생성.
    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
    }

    
    /**
	 * impUid로 결제내역 조회.
	 * @param impUid
	 * @return
	 * @throws IamportResponseException
	 * @throws IOException
	 */
    public IamportResponse<Payment> paymentLookup(@PathVariable("imp_uid") String imp_uid) 
    		throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(imp_uid);
    }
    
    /**
	 * impUid를 결제 번호로 찾고, 조회해야하는 경우.<br>
	 * 오버로딩.
	 * @param paymentsNo
	 * @return
	 * @throws IamportResponseException
	 * @throws IOException
	 */
	public IamportResponse<Payment> paymentLookup(long paymentsNo) 
			throws IamportResponseException, IOException {
		PaymentsInfoEntity paymentsInfo = paymentService.paymentLookup(paymentsNo);
		return iamportClient.paymentByImpUid(paymentsInfo.getImpUid());
	}
	
	/**
	 * 결제검증을 위한 메서드<br>
	 * map에는 imp_uid, amount, actionBoardNo 이 키값으로 넘어옴.
	 * @param map
	 * @return
	 * @throws IamportResponseException
	 * @throws IOException
	 * @throws verifyIamportException
	 */
	@PostMapping("/verifyIamport")
	public ResponseEntity<ResponseDto> verifyIamport(@RequestBody Map<String,String> map) 
			throws IamportResponseException, IOException {
		String userEmail = map.get("user_email");
		String impUid = map.get("imp_uid");//실제 결제금액 조회위한 아임포트 서버쪽에서 id
//		long actionBoardNo = Long.parseLong(map.get("actionBoardNo")); //DB에서 물건 가격 조회를 위한 번호
//		int amount = Integer.parseInt(map.get("amount"));//실제로 유저가 결제한 금액
		System.out.println("userEmail: " + userEmail);
		System.out.println("impUid: " + impUid);
		//아임포트 서버쪽에 결제된 정보 조회.
		//paymentByImpUid 는 아임포트에 제공해주는 api인 결제내역 조회(/payments/{imp_uid})의 역할을 함. 
		IamportResponse<Payment> irsp = paymentLookup(impUid);
		System.out.println("irsp: " + irsp);
		ResponseEntity<ResponseDto> result = paymentService.verifyIamport(irsp, userEmail);
		
		return result;
	}
}
