package com.soon.board.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soon.board.constant.ItemSellStatus;
import com.soon.board.dto.request.item.SearchItemRequestDto;
import com.soon.board.entity.ItemEntity;
import com.soon.board.entity.QItemEntity;

import javax.persistence.EntityManager;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	public ItemSellStatus getItemSellStatus(String sellStatus) {
        return convertToEnum(sellStatus);
    }

    private ItemSellStatus convertToEnum(String sellStatus) {
        if (sellStatus == null || sellStatus.isEmpty()) {
            throw new IllegalArgumentException("Sell status cannot be null or empty");
        }

        try {
            return ItemSellStatus.valueOf(sellStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid sell status: " + sellStatus, e);
        }
    }
    
	private BooleanExpression searchSellStatusEq(String sellStatus){
		System.out.println("sellStatus: " + sellStatus);
        return (sellStatus == null) || (sellStatus.equals("ALL") || ("undefined".equals(sellStatus))) ? null : QItemEntity.itemEntity.itemSellStatus.eq(getItemSellStatus(sellStatus));
    }

    private BooleanExpression regTimeBetween(String startDate, String endDate){
    	LocalDateTime start = startDate != null && !startDate.isEmpty() && !"undefined".equals(startDate) ? LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay() : null;
        LocalDateTime end = endDate != null && !endDate.isEmpty() && !"undefined".equals(endDate) ? LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay() : null;

        return (start == null || end == null || "undefined".equals(start) || "undefined".equals(end)) ? null : QItemEntity.itemEntity.regTime.between(start, end);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
    	if (searchQuery != null || (!"undefined".equals(searchBy) || !"undefined".equals(searchQuery))) {
    		if(StringUtils.equals("itemName", searchBy)){
    			return QItemEntity.itemEntity.itemName.like("%" + searchQuery + "%");
    		} else if(StringUtils.equals("writerEmail", searchBy)){
    			return QItemEntity.itemEntity.writerEmail.like("%" + searchQuery + "%");
    		}
    	}

        return null;
    }
    
    @Override
    public List<ItemEntity> getSearchItemList(SearchItemRequestDto itemSearchDto) {

    	List<ItemEntity> content = queryFactory
                .selectFrom(QItemEntity.itemEntity)
                .where(regTimeBetween(itemSearchDto.getStartDate(), itemSearchDto.getEndDate()),
                        searchSellStatusEq(itemSearchDto.getItemSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItemEntity.itemEntity.itemId.desc())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QItemEntity.itemEntity)
        		.where(regTimeBetween(itemSearchDto.getStartDate(), itemSearchDto.getEndDate()),
                        searchSellStatusEq(itemSearchDto.getItemSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .fetchOne();

        return content;
    }

    

     
}
