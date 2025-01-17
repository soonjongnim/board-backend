package com.soon.board.repository.resultSet;

import java.time.LocalDateTime;

import com.soon.board.constant.ItemSellStatus;

public interface GetItemResultSet {
	Integer getItemId();
	String getItemName();
	Integer getPrice();
	Integer getStockNumber();
	String getItemDetail();
	ItemSellStatus getItemSellStatus();
	LocalDateTime getRegTime();
	LocalDateTime getUpdateTime();
	String getWriterEmail();
}
