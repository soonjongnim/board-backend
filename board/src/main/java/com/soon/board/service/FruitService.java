package com.soon.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soon.board.dao.FruitMapper;
import com.soon.board.dto.Fruit;

@Service
public class FruitService {
	@Autowired FruitMapper fruitMapper;
	
	public List<Fruit> getList() {
		System.out.println("getFruit");
		return fruitMapper.getList();
	}
}
