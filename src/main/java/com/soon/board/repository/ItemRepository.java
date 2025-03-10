package com.soon.board.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soon.board.dto.request.item.SearchItemRequestDto;
import com.soon.board.entity.ItemEntity;
import com.soon.board.repository.resultSet.GetItemResultSet;

@Repository
//@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<ItemEntity, Integer>,
	QuerydslPredicateExecutor<ItemEntity>, ItemRepositoryCustom {
	List<ItemEntity> findByOrderByRegTimeDesc();
	
	@Query(value = "SELECT i.item_id, "
			  + "i.item_name, "
			  + "i.item_sell_status, "
			  + "i.price, "
			  + "i.reg_time, "
			  + "i.stock_number, "
			  + "i.update_time, "
			  + "i.writer_email, "
			  + "i.item_detail, "
	          + "(SELECT t.thumbnail_url "
	          + "FROM thumbnail_images t "
	          + "WHERE t.item_id = i.item_id "
	          + "ORDER BY t.id ASC "
	          + "LIMIT 1) AS thumbnail_url "
	        + "FROM items i "
	        + "ORDER BY i.reg_time DESC", 
	        nativeQuery = true)
    List<ItemEntity> findItemsWithFirstThumbnailOrderedByRegTimeDesc();
	
	@Query(value="SELECT * FROM items "
//		    + "WHERE item_name LIKE CONCAT('%', :itemName, '%')",
			+ "WHERE (:searchDateType IS NOT NULL "
	        + "OR (:searchDateType = 'created' AND reg_time BETWEEN :startDate AND :endDate)) "
	        + "AND (:itemName IS NOT NULL OR item_name LIKE CONCAT('%', :itemName, '%')) "
	        + "AND (:itemSellStatuss IS NOT NULL OR item_sell_status IN :itemSellStatuss) "
	        + "AND (:itemIds IS NOT NULL OR item_id IN :itemIds)",
		    nativeQuery=true)
	List<ItemEntity> getSearchItemList(@Param("itemIds") List<String> itemIds,
			@Param("searchDateType") String searchDateType,
			@Param("itemName") String itemName,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("itemSellStatuss") List<String> itemSellStatuss
            );
	
	@Query(
		value=
		"SELECT " +
		"item_id AS itemId, " +
		"item_name AS itemName, " +
		"price, " +
		"stock_number AS stockNumber, " +
		"item_detail AS itemDetail, " +
		"item_sell_status AS itemSellStatus, " +
		"reg_time AS regTime, " +
		"update_time AS updateTime, " +
		"writer_email AS writerEmail " +
		"FROM items " +
		"WHERE item_id = ?1 ",
		nativeQuery=true
	)
	GetItemResultSet getItem(Integer itemId);
	
	ItemEntity findByItemId(Integer itemId);
}
