package com.soon.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soon.board.dto.Fruit;

@Mapper
public interface FruitMapper {
	List<Fruit> getList();
}
