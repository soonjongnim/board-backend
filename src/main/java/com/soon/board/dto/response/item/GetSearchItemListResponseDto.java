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
public class GetSearchItemListResponseDto extends ResponseDto {

	private List<ItemListItem> searchList;
	private int totalCount;
	
	private GetSearchItemListResponseDto(List<ItemEntity> itemListEntities, int totalCount) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.searchList = ItemListItem.getList(itemListEntities);
		this.totalCount = totalCount;
	}
	
	public static ResponseEntity<GetSearchItemListResponseDto> success(List<ItemEntity> itemListEntities, int totalCount) {
		GetSearchItemListResponseDto result = new GetSearchItemListResponseDto(itemListEntities, totalCount);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
