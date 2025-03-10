package com.soon.board.dto.request.item;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.soon.board.constant.ItemSellStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchItemRequestDto {
	@NotBlank(message = "상품명은 필수 입력 값입니다.")
	private String itemName;	// 상품명
	@NotNull(message = "가격은 필수 입력 값입니다.")
	private Integer price;	// 가격
	@NotNull(message = "재고수량은 필수 입력 값입니다.")
	private Integer stockNumber;	// 재고수량
	
//	@NotBlank(message = "상품 상세 설명은 필수 입력 값입니다.")
	private String itemDetail;	// 상품 상세 설명
	private ItemSellStatus itemSellStatus;	// 상품 판매 상태
	private LocalDateTime updateTime;	// 수정 시간
	private String writerEmail;	// 작성자
	private List<String> imageUrlList;	// 이미지 리스트
	@NotNull
	private List<String> thumbnailUrlList; // ✅ 썸네일 리스트로 받음
}
