package com.soon.board.repository;

import java.util.List;

import com.soon.board.dto.request.item.SearchItemRequestDto;
import com.soon.board.entity.ItemEntity;

public interface ItemRepositoryCustom {
	List<ItemEntity> getSearchItemList(SearchItemRequestDto searchParams);
}
