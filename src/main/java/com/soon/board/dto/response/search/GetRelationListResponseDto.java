package com.soon.board.dto.response.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.soon.board.common.ResponseCode;
import com.soon.board.common.ResponseMessage;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.repository.resultSet.GetRelationListResultSet;

import lombok.Getter;

@Getter
public class GetRelationListResponseDto extends ResponseDto {

	private List<String> relationWordList;
	
	private GetRelationListResponseDto(List<GetRelationListResultSet> resultSets) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		
		List<String> relationWordList = new ArrayList<>();
		for (GetRelationListResultSet resultSet: resultSets) {
			String relationWord = resultSet.getSearchWord();
			relationWordList.add(relationWord);
		}
		this.relationWordList = relationWordList;
	}
	
	public static ResponseEntity<GetRelationListResponseDto> success(List<GetRelationListResultSet> resultSets) {
		GetRelationListResponseDto result = new GetRelationListResponseDto(resultSets);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
