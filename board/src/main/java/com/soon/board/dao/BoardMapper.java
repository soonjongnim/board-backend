package com.soon.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soon.board.common.dto.SearchDto;
import com.soon.board.entity.BoardEntity;

@Mapper
public interface BoardMapper {
	List<BoardEntity> findAll(SearchDto params);
	int countAll(SearchDto params);
}
