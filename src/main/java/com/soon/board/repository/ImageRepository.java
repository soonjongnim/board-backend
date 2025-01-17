package com.soon.board.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soon.board.entity.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer>{

	List<ImageEntity> findByBoardNumberAndType(Integer number, String type);
	List<ImageEntity> findByItemIdAndType(Integer number, String type);
	
	@Transactional
	void deleteByBoardNumberAndType(Integer number, String type);
	
	@Transactional
	void deleteByItemIdAndType(Integer number, String type);
	
	@Transactional
	void deleteByItemIdAndImageAndType(Integer itemId, String delImageUrl, String type);

}
