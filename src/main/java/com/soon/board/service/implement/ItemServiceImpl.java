package com.soon.board.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.soon.board.dto.ThumbnailFileDto;
import com.soon.board.dto.request.item.PatchItemRequestDto;
import com.soon.board.dto.request.item.PostItemRequestDto;
import com.soon.board.dto.request.item.SearchItemRequestDto;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.item.DeleteItemResponseDto;
import com.soon.board.dto.response.item.GetItemListResponseDto;
import com.soon.board.dto.response.item.GetItemResponseDto;
import com.soon.board.dto.response.item.GetSearchItemListResponseDto;
import com.soon.board.dto.response.item.PatchItemResponseDto;
import com.soon.board.dto.response.item.PostItemResponseDto;
import com.soon.board.entity.ImageEntity;
import com.soon.board.entity.ItemEntity;
import com.soon.board.entity.ThumbnailEntity;
import com.soon.board.repository.ImageRepository;
import com.soon.board.repository.ItemRepository;
import com.soon.board.repository.ThumbnailRepository;
import com.soon.board.repository.UserRepository;
import com.soon.board.repository.resultSet.GetItemResultSet;
import com.soon.board.service.FileService;
import com.soon.board.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired private UserRepository userRepository;
	@Autowired private ItemRepository itemRepository;
	@Autowired private ImageRepository imageRepository;
	@Autowired private FileService fileService;
	@Autowired private ThumbnailRepository thumbnailRepository;
	
	@Override
	public ResponseEntity<? super PostItemResponseDto> postItem(PostItemRequestDto dto) {
		try {
			ItemEntity itemEntity = new ItemEntity(dto);
			System.out.println("itemService dto: " + dto);
			// 데이터베이스에 엔티티 저장
			itemRepository.save(itemEntity);
			
			// 저장된 엔티티에서 itemId 가져오기
			int itemId = itemEntity.getItemId();
			System.out.println("Saved Item ID: " + itemId);
			
			List<String> imageUrlList = dto.getImageUrlList();
			System.out.println("imageUrlList: " + imageUrlList);
			if (imageUrlList != null && !imageUrlList.isEmpty()) { // ✅ 빈 배열이 아닐 때만 실행
			    List<ImageEntity> imageEntities = new ArrayList<>();

			    for (String image : imageUrlList) {
			        ImageEntity imageEntity = new ImageEntity(itemId, image, "ITEM");
			        imageEntities.add(imageEntity);
			    }

			    imageRepository.saveAll(imageEntities);
			} else {
			    System.out.println("imageUrlList가 비어있거나 null입니다. 저장하지 않습니다.");
			}
			
			List<String> thumbnailUrlList = dto.getThumbnailUrlList();
            System.out.println("thumbnailUrlList: " + thumbnailUrlList);
			if (thumbnailUrlList != null && !thumbnailUrlList.isEmpty()) { // ✅ 빈 배열이 아닐 때만 실행
			    List<ThumbnailEntity> thumbnailEntities = new ArrayList<>();
			    int index = 0; // ✅ 인덱스 초기화
			    for (String thumbnailUrl : thumbnailUrlList) {
			    	ThumbnailEntity imageEntity = new ThumbnailEntity(itemId, thumbnailUrl, index);
			    	thumbnailEntities.add(imageEntity);
			        index++; // ✅ 인덱스 증가
			    }

			    thumbnailRepository.saveAll(thumbnailEntities);
			} else {
			    System.out.println("thumbnailUrlList가 비어있거나 null입니다. 저장하지 않습니다.");
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return PostItemResponseDto.success();
	}
	
//	@Override
//	public ResponseEntity<? super PostItemResponseDto> postItem(PostItemRequestDto dto, String email) {
//		try {
//			boolean existedEmail = userRepository.existsByEmail(email);
//			if (!existedEmail) return PostItemResponseDto.noExistUser();
//			
////			dto.setItemSellStatus(ItemSellStatus.NOTSALE);
////			System.out.println("dto: " + dto);
//			ItemEntity itemEntity = new ItemEntity(dto, email);
//			itemRepository.save(itemEntity);
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			return ResponseDto.databaseError();
//		}
//		return PostItemResponseDto.success();
//	}
	
	@Override
	public ResponseEntity<? super GetItemListResponseDto> getItemList() {
		
		List<ItemEntity> itemListEntities = new ArrayList<>();
		
		try {
			itemListEntities = itemRepository.findItemsWithFirstThumbnailOrderedByRegTimeDesc();
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetItemListResponseDto.success(itemListEntities);
	}
	
	@Override
	public ResponseEntity<? super GetSearchItemListResponseDto> getSearchItemList(SearchItemRequestDto searchParams) {
		List<ItemEntity> itemListEntities = new ArrayList<>();
		
		try {
			SearchItemRequestDto requestDto = new SearchItemRequestDto(searchParams);
			System.out.println("requestDto: " + requestDto);
			itemListEntities = itemRepository.getSearchItemList(requestDto);
//			itemListEntities = itemRepository.getSearchItemList(requestDto);
//			itemListEntities = itemRepository.getSearchItemList(
////					requestDto.getItemIds(),
//					requestDto.getSearchDateType(),
//					requestDto.getItemName(),
////					requestDto.getStartDate(),
////					requestDto.getEndDate(),
//					requestDto.getItemSellStatus()
//					);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetSearchItemListResponseDto.success(itemListEntities);
	}

	@Override
	public ResponseEntity<? super GetItemResponseDto> getItem(Integer itemId) {
		GetItemResultSet resultSet = null;
		List<ThumbnailEntity> thumbnailEntities = new ArrayList<>();
		
		try {
			resultSet = itemRepository.getItem(itemId);
			if (resultSet == null) return GetItemResponseDto.noExistItem();
			
			thumbnailEntities = thumbnailRepository.findByItemId(itemId);
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetItemResponseDto.success(resultSet, thumbnailEntities);
	}

	@Override
	public ResponseEntity<? super PatchItemResponseDto> patchItem(PatchItemRequestDto dto, Integer itemId) {
		try {
			ItemEntity itemEntity = itemRepository.findByItemId(itemId);
			System.out.println("itemEntity: " + itemEntity);
			if (itemEntity == null) return PatchItemResponseDto.noExistItem();
			
			String writerEmail = itemEntity.getWriterEmail();
			
			itemEntity.updateItem(dto, writerEmail);
			itemRepository.save(itemEntity);
			
			List<String> imageUrlList = dto.getImageUrlList();
			System.out.println("imageUrlList: " + imageUrlList);
			if (imageUrlList != null && !imageUrlList.isEmpty()) { // ✅ 빈 배열이 아닐 때만 실행
			    List<ImageEntity> imageEntities = new ArrayList<>();

			    for (String image : imageUrlList) {
			        ImageEntity imageEntity = new ImageEntity(itemId, image, "ITEM");
			        imageEntities.add(imageEntity);
			    }

			    imageRepository.saveAll(imageEntities);
			} else {
			    System.out.println("imageUrlList가 비어있거나 null입니다. 저장하지 않습니다.");
			}
			
			List<String> thumbnailUrlList = dto.getThumbnailUrlList();
            System.out.println("thumbnailUrlList: " + thumbnailUrlList);
			if (thumbnailUrlList != null && !thumbnailUrlList.isEmpty()) { // ✅ 빈 배열이 아닐 때만 실행
			    List<ThumbnailEntity> thumbnailEntities = new ArrayList<>();
			    int index = 0; // ✅ 인덱스 초기화
			    for (String thumbnailUrl : thumbnailUrlList) {
			    	ThumbnailEntity imageEntity = new ThumbnailEntity(itemId, thumbnailUrl, index);
			    	thumbnailEntities.add(imageEntity);
			        index++; // ✅ 인덱스 증가
			    }

			    thumbnailRepository.saveAll(thumbnailEntities);
			} else {
			    System.out.println("thumbnailUrlList가 비어있거나 null입니다. 저장하지 않습니다.");
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return PatchItemResponseDto.success();
	}
	
//	@Override
//	public ResponseEntity<? super PatchItemResponseDto> patchItem(@Valid PatchItemRequestDto dto, Integer itemId,
//			String email) {
//		try {
//			boolean existedUser = userRepository.existsByEmail(email);
//			if (!existedUser) return PatchItemResponseDto.noExistUser();
//			
//			ItemEntity itemEntity = itemRepository.findByItemId(itemId);
//			System.out.println("itemEntity: " + itemEntity);
//			if (itemEntity == null) return PatchItemResponseDto.noExistItem();
//			
//			String writerEmail = itemEntity.getWriterEmail();
//			boolean isWriter = writerEmail.equals(email);
//			if (!isWriter) return PatchItemResponseDto.noPermission();
//			
//			itemEntity.updateItem(dto, writerEmail);
//			itemRepository.save(itemEntity);
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			return ResponseDto.databaseError();
//		}
//		return PatchItemResponseDto.success();
//	}

	@Transactional
	@Override
	public ResponseEntity<? super DeleteItemResponseDto> deleteItem(Integer itemId) {
		try {
			// Step 1: Find the item by ID
			ItemEntity itemEntity = itemRepository.findByItemId(itemId);
			if (itemEntity == null) return DeleteItemResponseDto.noExistItem();
			// Step 2: Delete associated cloud files
			fileService.cloudDelete(itemId, null, "ITEM");
			// Step 3: Delete associated images from the repository
			imageRepository.deleteByItemIdAndType(itemId, "ITEM");
			// Step 4: Delete the item itself
			itemRepository.delete(itemEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return DeleteItemResponseDto.success();
	}
//	@Override
//	public ResponseEntity<? super DeleteItemResponseDto> deleteItem(Integer itemId, String email) {
//		try {
//			boolean existedUser = userRepository.existsByEmail(email);
//			if (!existedUser) return DeleteItemResponseDto.notExistUser();
//			
//			ItemEntity itemEntity = itemRepository.findByItemId(itemId);
//			if (itemEntity == null) return DeleteItemResponseDto.noExistItem();
//			
//			String writerEmail = itemEntity.getWriterEmail();
//			boolean isWriter = writerEmail.equals(email);
//			if (!isWriter) return DeleteItemResponseDto.noPermission();
//			
//			itemRepository.delete(itemEntity);
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			return ResponseDto.databaseError();
//		}
//		return DeleteItemResponseDto.success();
//	}

	
}
