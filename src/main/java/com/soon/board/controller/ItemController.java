package com.soon.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soon.board.dto.ThumbnailFileDto;
import com.soon.board.dto.request.board.PatchBoardRequestDto;
import com.soon.board.dto.request.item.PatchItemRequestDto;
import com.soon.board.dto.request.item.PostItemRequestDto;
import com.soon.board.dto.request.item.SearchItemRequestDto;
import com.soon.board.dto.response.board.GetBoardResponseDto;
import com.soon.board.dto.response.board.GetSearchBoardListResponseDto;
import com.soon.board.dto.response.board.PatchBoardResponseDto;
import com.soon.board.dto.response.item.DeleteItemResponseDto;
import com.soon.board.dto.response.item.GetItemResponseDto;
import com.soon.board.dto.response.item.GetSearchItemListResponseDto;
import com.soon.board.dto.response.item.GetItemListResponseDto;
import com.soon.board.dto.response.item.PatchItemResponseDto;
import com.soon.board.dto.response.item.PostItemResponseDto;
import com.soon.board.service.ItemService;

@RestController
@RequestMapping("/api/item")
public class ItemController {
	
	@Autowired private ItemService itemService;
	
	@PostMapping("")
	public ResponseEntity<? super PostItemResponseDto> postItem(
			@RequestBody @Valid PostItemRequestDto requestBody) {		
		try {
			System.out.println("requestBody: " + requestBody);
//			List<ThumbnailFileDto> thumbnails = requestBody.getThumbnailUrlList();
//            System.out.println("썸네일: " + thumbnails);
//            System.out.println("썸네일 개수: " + thumbnails.size());
//
//            for (ThumbnailFileDto file : thumbnails) {
//                System.out.println("파일명: " + file.getName());
//                System.out.println("파일 크기: " + file.getSize() + " bytes");
//                System.out.println("파일 타입: " + file.getType());
//                System.out.println("파일 수정 날짜: " + file.getLastModified());
//            }
			ResponseEntity<? super PostItemResponseDto> response = itemService.postItem(requestBody);
			System.out.println("response: " + response);
			return ResponseEntity.ok("상품이 성공적으로 생성되었습니다.");
//			return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
	}
//	@PostMapping("")
//	public ResponseEntity<? super PostItemResponseDto> postItem(
//			@RequestBody @Valid PostItemRequestDto requestBody,
//			@AuthenticationPrincipal String email) {		
//		System.out.println("requestBody: " + requestBody);
//		ResponseEntity<? super PostItemResponseDto> response = itemService.postItem(requestBody, email);
//		System.out.println("response: " + response);
//		return response;
//	}
	
	@GetMapping("/list")
	public ResponseEntity<? super GetItemListResponseDto> getItemList() {
		ResponseEntity<? super GetItemListResponseDto> response = itemService.getItemList();
		return response;
	}
	
	@GetMapping("/search-list/{searchParams}")
	public ResponseEntity<? super GetSearchItemListResponseDto> getSearchItemList(
			@PathVariable(value="searchParams", required=false) SearchItemRequestDto searchParams) {
		System.out.println("search-list searchParams: " + searchParams);
		
		ResponseEntity<? super GetSearchItemListResponseDto> response = itemService.getSearchItemList(searchParams);
		System.out.println("response: " + response);
		return response;
	}
	
	@GetMapping("/{itemId}")
	public ResponseEntity<? super GetItemResponseDto> getItem(@PathVariable("itemId") Integer itemId) {
		ResponseEntity<? super GetItemResponseDto> response = itemService.getItem(itemId);
		return response;
	}
	
	
	// @AuthenticationPrincipal 체크 안한것
	@PatchMapping("/{itemId}")
	public ResponseEntity<? super PatchItemResponseDto> patchItem(
			@RequestBody @Valid PatchItemRequestDto dto,
			@PathVariable("itemId") Integer itemId) {
		ResponseEntity<? super PatchItemResponseDto> response = itemService.patchItem(dto, itemId);
		return response;
	}
	
	// @AuthenticationPrincipal 체크한것
//	@PatchMapping("/{itemId}")
//	public ResponseEntity<? super PatchItemResponseDto> patchItem(
//			@RequestBody @Valid PatchItemRequestDto dto,
//			@PathVariable("itemId") Integer itemId,
//			@AuthenticationPrincipal String email) {
//		ResponseEntity<? super PatchItemResponseDto> response = itemService.patchItem(dto, itemId, email);
//		return response;
//	}
	
	@DeleteMapping("/{itemId}")
	public ResponseEntity<? super DeleteItemResponseDto> deleteItem(
			@PathVariable("itemId") Integer itemId) {
		ResponseEntity<? super DeleteItemResponseDto> response = itemService.deleteItem(itemId);
		return response;
	}
//	@DeleteMapping("/{itemId}")
//	public ResponseEntity<? super DeleteItemResponseDto> deleteItem(
//			@PathVariable("itemId") Integer itemId, 
//			@AuthenticationPrincipal String email) {
//		ResponseEntity<? super DeleteItemResponseDto> response = itemService.deleteItem(itemId, email);
//		return response;
//	}

}
