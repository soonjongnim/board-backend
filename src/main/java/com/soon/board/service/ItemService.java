package com.soon.board.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.soon.board.dto.request.item.PatchItemRequestDto;
import com.soon.board.dto.request.item.PostItemRequestDto;
import com.soon.board.dto.request.item.SearchItemRequestDto;
import com.soon.board.dto.response.item.DeleteItemResponseDto;
import com.soon.board.dto.response.item.GetItemResponseDto;
import com.soon.board.dto.response.item.GetSearchItemListResponseDto;
import com.soon.board.dto.response.item.GetItemListResponseDto;
import com.soon.board.dto.response.item.PatchItemResponseDto;
import com.soon.board.dto.response.item.PostItemResponseDto;

public interface ItemService {
	ResponseEntity<? super PostItemResponseDto> postItem(PostItemRequestDto dto);
//	ResponseEntity<? super PostItemResponseDto> postItem(PostItemRequestDto dto, String email);
	ResponseEntity<? super GetItemListResponseDto> getItemList();
	ResponseEntity<? super GetSearchItemListResponseDto> getSearchItemList(SearchItemRequestDto searchParams);
	ResponseEntity<? super GetItemResponseDto> getItem(Integer itemId);
	ResponseEntity<? super PatchItemResponseDto> patchItem(PatchItemRequestDto dto, Integer itemId);
//	ResponseEntity<? super PatchItemResponseDto> patchItem(@Valid PatchItemRequestDto dto, Integer itemId, String email);
	ResponseEntity<? super DeleteItemResponseDto> deleteItem(Integer itemId);
//	ResponseEntity<? super DeleteItemResponseDto> deleteItem(Integer itemId, String email);
}
