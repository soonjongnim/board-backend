package com.soon.board.dto.request.item;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.soon.board.constant.ItemSellStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchItemRequestDto {
//	private List<String> itemIds;	// 상품id
//	private String searchDateType;	// 생성일 기준 아님 수정일기준
//	private String itemName;	// 상품명
	private String itemSellStatus;	// 상품 판매 상태
    private String searchBy;
    private String searchQuery = "";
	private String startDate;	// 시작시간
	private String endDate;	// 종료시간
	
	public SearchItemRequestDto(SearchItemRequestDto searchParams) {
		this.itemSellStatus = searchParams.getItemSellStatus();
		this.searchBy = searchParams.getSearchBy();
		this.searchQuery = searchParams.getSearchQuery();
		this.startDate = searchParams.getStartDate();
		this.endDate = searchParams.getEndDate();
	}
	
	public SearchItemRequestDto(String searchParams) {
		Map<String, String[]> queryParams = parseQueryParams(searchParams);
//		this.itemIds = getListValue(queryParams, "itemIds");
//		this.searchDateType = getSingleValue(queryParams, "searchDateType");
		this.itemSellStatus = getSingleValue(queryParams, "itemSellStatus");
		this.searchBy = getSingleValue(queryParams, "searchBy");
		this.searchQuery = getSingleValue(queryParams, "searchQuery");
		this.startDate = getSingleValue(queryParams, "startDate");
		this.endDate = getSingleValue(queryParams, "endDate");
//		this.itemSellStatuss = getListValue(queryParams, "itemSellStatuss");
	}
	
	private Map<String, String[]> parseQueryParams(String searchParams) {
		Map<String, String[]> queryParams = new HashMap<>();
		String[] pairs = searchParams.split("&");
		for (String pair : pairs) {
			String[] keyValue = pair.split("=");
//			System.out.println("keyValue[0]: " + keyValue[0]);
//			System.out.println("keyValue[1]: " + keyValue[1]);
			if (keyValue.length == 2) { // 쿼리 문자열이 올바른 형식인지 확인
	            String key = keyValue[0];
	            String value = null;
	            try {
	                value = URLDecoder.decode(keyValue[1], "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	            if (!queryParams.containsKey(key)) {
	                queryParams.put(key, new String[]{value});
	            } else {
	                String[] existingValues = queryParams.get(key);
	                String[] newValues = new String[existingValues.length + 1];
	                System.arraycopy(existingValues, 0, newValues, 0, existingValues.length);
	                newValues[newValues.length - 1] = value;
	                queryParams.put(key, newValues);
	            }
	        }
		}
		return queryParams;
	}
	
	private String getSingleValue(Map<String, String[]> queryParams, String key) {
		String[] values = queryParams.get(key);
		return (values != null && values.length > 0) ? values[0] : null;
	}
	
	private List<String> getListValue(Map<String, String[]> queryParams, String key) {
		String[] values = queryParams.get(key);
		if (values != null) {
			List<String> listValues = new ArrayList<>();
			for (String value : values) {
				listValues.add(value);
			}
			return listValues;
		}
		return null;
	}
	
}
