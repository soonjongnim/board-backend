package com.soon.board.entity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.Valid;

import com.soon.board.constant.ItemSellStatus;
import com.soon.board.dto.request.item.PatchItemRequestDto;
import com.soon.board.dto.request.item.PostItemRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="items")
public class ItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int itemId;	// 상품 코드
	
	@Column(nullable = false, length = 50)
	private String itemName;	// 상품명
	
	@Column(name="price", nullable = false)
	private int price;	// 가격
	
	@Column(nullable = false)
	private int stockNumber;	// 재고수량
	
	@Lob
	@Column(nullable = false)
	private String itemDetail;	// 상품 상세 설명
	
	@Enumerated(EnumType.STRING)
	private ItemSellStatus itemSellStatus;	// 상품 판매 상태
	
	private LocalDateTime regTime;	// 등록 시간
	private LocalDateTime updateTime;	// 수정 시간
	private String writerEmail; // 작성자
	
	public ItemEntity(PostItemRequestDto dto) {
			
//		Date now = Date.from(Instant.now());
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String writeDatetime = simpleDateFormat.format(now);
		
		this.itemName = dto.getItemName();
		this.price = dto.getPrice();
		this.itemDetail = dto.getItemDetail();
		this.stockNumber = dto.getStockNumber();
		this.itemSellStatus = dto.getItemSellStatus();
		this.regTime = LocalDateTime.now();
		this.writerEmail = dto.getWriterEmail();
	}
	
	public ItemEntity(PostItemRequestDto dto, String email) {
		
//		Date now = Date.from(Instant.now());
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String writeDatetime = simpleDateFormat.format(now);
		
		this.itemName = dto.getItemName();
		this.price = dto.getPrice();
		this.itemDetail = dto.getItemDetail();
		this.stockNumber = dto.getStockNumber();
		this.itemSellStatus = dto.getItemSellStatus();
		this.regTime = LocalDateTime.now();
		this.writerEmail = email;
	}

	public void updateItem(@Valid PatchItemRequestDto dto, String email){
        this.itemName = dto.getItemName();
        this.price = dto.getPrice();
        this.stockNumber = dto.getStockNumber();
        this.itemDetail = dto.getItemDetail();
        this.itemSellStatus = dto.getItemSellStatus();
        this.updateTime = LocalDateTime.now();
		this.writerEmail = email;
    }
}
