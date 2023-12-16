package com.soon.board.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 결제 정보 entity
 * 
 * 결제 번호
 * 결제 방법
 * 주문 번호
 * 구매 번호
 * 가격
 * 구매자 주소
 * 구매자 우편
 * 판매 게시글
 * 구매자 
 * @author USER
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="payments_info")
@Table(name="payments_info") // 데이터베이스에 해당 테이블과 현재 클래스를 매핑 시키겠다.
public class PaymentsInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT (기본키 생성)
	private long paymentsNo;
	
	@Column(nullable = false, length = 50)
	private String pgProvider;
	
	@Column(nullable = false, length = 50)
	private String payMethod;
	
	@Column(nullable = true, length = 100)
	private String impUid;
	
	@Column(nullable = false, length = 100)
	private String merchantUid;
	
	@Column(nullable = false, length = 200)
	private String productName;
	
	@Column(nullable = false)
	private int amount;
	
	private int taxFree;
	
	private int vatAmount;
	
	@Column(nullable = false, length = 300)
	private String pgTid;
	
	@Column(nullable = false, length = 50)
	private String buyerName;
	
	@Column(nullable = false, length = 100)
	private String buyerEmail;
	
	@Column(nullable = false, length = 50)
	private String buyerTel;
	
	@Column(nullable = false, length = 100)
	private String buyerAddr;
	
	@Column(nullable = false, length = 100)
	private String buyerPostcode;

	private LocalDateTime buyDatetime;
	
	private String paidAt;

	private String status;
	
	private boolean success;

	private int returnCode;
	
	private String returnMsg;
	
	@Column(nullable = false, length = 50)
	private String userEmail;
	
}
