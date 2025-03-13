package com.soon.board.service.implement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soon.board.constant.ItemSellStatus;
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
			System.out.println("getItemList itemListEntities: " + itemListEntities);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetItemListResponseDto.success(itemListEntities);
	}
	
	@Override
	public ResponseEntity<? super GetSearchItemListResponseDto> getSearchItemList(SearchItemRequestDto searchParams) {
		List<Object[]> result = new ArrayList<>();
		List<ItemEntity> itemListEntities = new ArrayList<>();
		int totalCount = 0; // ✅ total_count 저장할 변수
		
		try {
			SearchItemRequestDto requestDto = new SearchItemRequestDto(searchParams);
			System.out.println("requestDto: " + requestDto);
			int itemsPerPage = (requestDto.getItemsPerPage() == null) ? 5 : Integer.parseInt(requestDto.getItemsPerPage());
		    int page = (requestDto.getPage() == null) ? 1 : Integer.parseInt(requestDto.getPage());
		    int offset = (page - 1) * itemsPerPage; // OFFSET 계산
			result = itemRepository.getSearchItemList(
					requestDto.getSearchBy(),
					requestDto.getSearchQuery(),
	                requestDto.getStartDate() != null ? requestDto.getStartDate() : null,
	                requestDto.getEndDate() != null ? requestDto.getEndDate() : null,
	                requestDto.getItemSellStatus(),
	                itemsPerPage, 
	                offset
			);

	        for (Object[] row : result) {
	        	ItemSellStatus status = null;
	        	if (row[2] != null) {
	        	    try {
	        	        status = ItemSellStatus.valueOf((String) row[2]); // ✅ String → Enum 변환
	        	    } catch (IllegalArgumentException e) {
	        	        status = ItemSellStatus.NOT_SALE;  // NULL경우 기본값 설정 (예: NOT_SALE)
	        	    }
	        	}
	        	
	        	ItemEntity dto = new ItemEntity(
	        			((Number) row[0]).intValue(),  // itemId 
	        		    (String) row[1],               // itemName
	        		    status,               			// itemSellStatus
	        		    ((Number) row[3]).intValue(),  // price 
	        		    row[4] != null ? ((Timestamp) row[4]).toLocalDateTime() : null, // regTime
	        		    ((Number) row[5]).intValue(),  // stockNumber 
	        		    row[6] != null ? ((Timestamp) row[6]).toLocalDateTime() : null, // updateTime
	        		    (String) row[7],               // writerEmail
	        		    (String) row[8]                // itemDetail
	        	    );
	        	itemListEntities.add(dto);
	        	
	            // ✅ total_count는 모든 행에서 동일하므로 첫 번째 행에서만 가져오기
	        	if (result != null && !result.isEmpty()) {
	        	    totalCount = ((Number) result.get(0)[9]).intValue();  // ✅ total_count 컬럼 값
	        	} else {
	        	    totalCount = 0;
	        	}
	        }
	        System.out.println("itemListEntities: " + itemListEntities);
	        System.out.println("totalCount: " + totalCount);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetSearchItemListResponseDto.success(itemListEntities, totalCount);
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
