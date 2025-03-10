package com.soon.board.dto.response.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.constant.ItemSellStatus;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.entity.ImageEntity;
import com.soon.board.entity.ThumbnailEntity;
import com.soon.board.repository.resultSet.GetItemResultSet;

import lombok.Getter;

@Getter
public class GetItemResponseDto extends ResponseDto {

	private int itemId;
	private String itemName;
	private Integer price;
	private Integer stockNumber;
	private String itemDetail;
	private ItemSellStatus itemSellStatus;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	private String writerEmail;
	private List<String> thumbnailList;
	
	private GetItemResponseDto(GetItemResultSet resultSet, List<ThumbnailEntity> thumbnailEntities) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		
		List<String> getThumbnailList = new ArrayList<>();
		for (ThumbnailEntity thumbnailEntitie: thumbnailEntities) {
			String thumbnail = thumbnailEntitie.getThumbnailUrl();
			getThumbnailList.add(thumbnail);
		}
		
		this.itemId = resultSet.getItemId();
		this.itemName = resultSet.getItemName();
		this.price = resultSet.getPrice();
		this.stockNumber = resultSet.getStockNumber();
		this.itemDetail = resultSet.getItemDetail();
		this.itemSellStatus = resultSet.getItemSellStatus();
		this.regTime = resultSet.getRegTime();
		this.updateTime = resultSet.getUpdateTime();
		this.writerEmail = resultSet.getWriterEmail();
		this.thumbnailList = getThumbnailList;
	}
	
	public static ResponseEntity<GetItemResponseDto> success(GetItemResultSet resultSet, List<ThumbnailEntity> thumbnailEntities) {
		GetItemResponseDto result = new GetItemResponseDto(resultSet, thumbnailEntities);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> noExistItem() {
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ITEMS, ResponseMessage.NOT_EXISTED_ITEMS);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}
