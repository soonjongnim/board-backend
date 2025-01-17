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
	

//    default List<ItemEntity> getSearchItemList(SearchItemRequestDto searchParams) {
//    	System.out.println("searchParams: " + searchParams);
//        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM items WHERE ");
//
//        // searchDateType이 null이 아닌 경우에만 생성일에 대한 조건 추가
//        if (searchParams.getSearchDateType() != null) {
//            queryBuilder.append("('").append(searchParams.getSearchDateType()).append("' IS NULL ");
//            queryBuilder.append("OR ('").append(searchParams.getSearchDateType()).append("' = 'created' ");
//            queryBuilder.append("AND reg_time BETWEEN '").append(searchParams.getStartDate()).append("' AND '").append(searchParams.getEndDate()).append("')) ");
//        }
//
//        // itemName이 null이 아닌 경우에만 상품명에 대한 조건 추가
//        if (searchParams.getItemName() != null) {
//            queryBuilder.append("AND (").append("NULL").append(" IS NULL ");
//            queryBuilder.append("OR item_name LIKE CONCAT('%', '").append(searchParams.getItemName()).append("', '%')) ");
//        }
//
//        // itemSellStatuss가 null이 아닌 경우에만 상품 판매 상태에 대한 조건 추가
//        if (searchParams.getItemSellStatuss() != null) {
//            queryBuilder.append("AND (").append("NULL").append(" IS NULL ");
//            queryBuilder.append("OR item_sell_status IN ").append("NULL").append(") ");
//        }
//
//        // itemIds가 null이 아닌 경우에만 상품 id에 대한 조건 추가
//        if (searchParams.getItemIds() != null) {
//            queryBuilder.append("AND (").append("NULL").append(" IS NULL ");
//            queryBuilder.append("OR item_id IN ").append("NULL").append(") ");
//        }
//
//        javax.persistence.Query query = entityManager.createNativeQuery(queryBuilder.toString(), ItemEntity.class);
//        @SuppressWarnings("unchecked")
//        List<ItemEntity> itemList = query.getResultList();
//        return itemList;
//    }
	
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
