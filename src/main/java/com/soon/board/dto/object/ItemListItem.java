package com.soon.board.dto.object;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.soon.board.constant.ItemSellStatus;
import com.soon.board.entity.ItemEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemListItem {

	private int itemId;
	private String itemName;
	private int price;
	private int stockNumber;
	private String itemDetail;
	private ItemSellStatus itemSellStatus;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	private String writerEmail;
	private String thumbnailUrl; // ✅ 메인 썸네일이미지
	
	public ItemListItem(ItemEntity itemListEntity) {
		this.itemId = itemListEntity.getItemId();
		this.itemName = itemListEntity.getItemName();
		this.price = itemListEntity.getPrice();
		this.stockNumber = itemListEntity.getStockNumber();
		this.itemDetail = itemListEntity.getItemDetail();
		this.itemSellStatus = itemListEntity.getItemSellStatus();
		this.regTime = itemListEntity.getRegTime();
		this.updateTime = itemListEntity.getUpdateTime();
		this.writerEmail = itemListEntity.getWriterEmail();
		this.thumbnailUrl = itemListEntity.getThumbnailUrl();
	}
	
	public static List<ItemListItem> getList(List<ItemEntity> itemListEntities) {
		List<ItemListItem> list = new ArrayList<>();
		for (ItemEntity itemListEntity: itemListEntities) {
			ItemListItem itemListItem = new ItemListItem(itemListEntity);
			list.add(itemListItem);
		}
		return list;
	}
	
}
