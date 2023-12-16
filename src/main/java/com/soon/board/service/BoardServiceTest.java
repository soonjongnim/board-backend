package com.soon.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soon.board.common.dto.SearchDto;
import com.soon.board.common.paging.Pagination;
import com.soon.board.common.paging.PagingResponse;
import com.soon.board.dao.BoardMapper;
import com.soon.board.entity.BoardEntity;
import com.soon.board.repository.BoardRepository;

@Service
public class BoardServiceTest {
	
	@Autowired BoardRepository boardRepository;
	@Autowired BoardMapper boardMapper;
	
	public PagingResponse<BoardEntity> findAllOfPage(SearchDto params) {
        
		// 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = boardMapper.countAll(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<BoardEntity> list = boardMapper.findAll(params);
        System.out.println("board: " + list);
        return new PagingResponse<>(list, pagination);
    }
}
