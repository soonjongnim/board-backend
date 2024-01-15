package com.soon.board.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.search.GetPopularListResponseDto;
import com.soon.board.dto.response.search.GetRelationListResponseDto;
import com.soon.board.repository.SearchLogRepository;
import com.soon.board.repository.resultSet.GetPopularListResultSet;
import com.soon.board.repository.resultSet.GetRelationListResultSet;
import com.soon.board.service.SearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImplement implements SearchService {
	
	private final SearchLogRepository searchLogRepository;
	
	@Override
	public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
		
		List<GetPopularListResultSet> resultSets = new ArrayList<>();
		
		try {
			resultSets = searchLogRepository.getPopularList();
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetPopularListResponseDto.success(resultSets);
	}

	@Override
	public ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord) {
		
		List<GetRelationListResultSet> resultSets = new ArrayList<>();
		
		try {
			resultSets = searchLogRepository.getRelationList(searchWord);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetRelationListResponseDto.success(resultSets);
	}

}
