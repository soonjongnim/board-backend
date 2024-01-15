package com.soon.board.dto.response.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.repository.resultSet.GetPopularListResultSet;

import lombok.Getter;

@Getter
public class GetPopularListResponseDto extends ResponseDto {

	List<String> popularWordList;
	
	private GetPopularListResponseDto(List<GetPopularListResultSet> resultSets) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		List<String> popularWordList = new ArrayList<>();
		for (GetPopularListResultSet resultSet: resultSets) {
			String popularWord = resultSet.getSearchWord();
			popularWordList.add(popularWord);
		}
		this.popularWordList = popularWordList;
	}
	
	public static ResponseEntity<GetPopularListResponseDto> success(List<GetPopularListResultSet> resultSets) {
		GetPopularListResponseDto result = new GetPopularListResponseDto(resultSets);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
