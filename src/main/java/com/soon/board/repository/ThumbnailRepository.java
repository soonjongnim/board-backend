package com.soon.board.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soon.board.entity.ThumbnailEntity;

@Repository
//@Transactional(readOnly = true)
public interface ThumbnailRepository extends JpaRepository<ThumbnailEntity, Integer> {
	 List<ThumbnailEntity> findByItemId(Integer itemId);
	 
	 @Transactional
	 void deleteByItemIdAndThumbnailUrl(Integer itemId, String delThumbnailUrl);
}
