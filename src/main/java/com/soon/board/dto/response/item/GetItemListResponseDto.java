package com.soon.board.dto.response.item;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.object.ItemListItem;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.entity.ItemEntity;

import lombok.Getter;

@Getter
public class GetItemListResponseDto extends ResponseDto {

	private List<ItemListItem> itemList;
	
	private GetItemListResponseDto(List<ItemEntity> itemListEntities) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.itemList = ItemListItem.getList(itemListEntities);
	}
	
	public static ResponseEntity<GetItemListResponseDto> success(List<ItemEntity> itemListEntities) {
		GetItemListResponseDto result = new GetItemListResponseDto(itemListEntities);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
