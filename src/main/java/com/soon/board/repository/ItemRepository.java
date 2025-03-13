package com.soon.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soon.board.entity.ItemEntity;
import com.soon.board.repository.resultSet.GetItemResultSet;

@Repository
//@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
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
	
    @Query(value = "SELECT item_id, " +
            "item_name, " +
            "item_sell_status, " +
            "price, " +
            "reg_time, " +
            "stock_number, " +
            "update_time, " +
            "writer_email, " +
            "item_detail, " +
            "COUNT(*) OVER() AS total_count " +
        "FROM items " +
        "WHERE ((:searchBy = 'itemName' AND :searchQuery IS NOT NULL AND item_name LIKE CONCAT('%', :searchQuery, '%')) " +
        "OR (:searchBy = 'writerEmail' AND :searchQuery IS NOT NULL AND writer_email LIKE CONCAT('%', :searchQuery, '%')) " +
        "OR (:searchQuery IS NULL)) " +
        "AND (:itemSellStatus IS NULL OR :itemSellStatus = '' OR :itemSellStatus = 'ALL' OR :itemSellStatus = 'undefined' OR item_sell_status = :itemSellStatus) " +
        "AND (:startDate IS NULL OR :endDate IS NULL OR reg_time BETWEEN :startDate AND :endDate) " +
        "LIMIT :itemsPerPage OFFSET :offset",
        nativeQuery = true)
	List<Object[]> getSearchItemList(
			@Param("searchBy") String searchBy,
			@Param("searchQuery") String searchQuery,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("itemSellStatus") String itemSellStatus,
            @Param("itemsPerPage") int itemsPerPage,
            @Param("offset") int offset
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
