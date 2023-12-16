package com.soon.board.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soon.board.dto.request.board.PostBoardRequestDto;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.board.PostBoardResponseDto;
import com.soon.board.entity.BoardEntity;
import com.soon.board.entity.ImageEntity;
import com.soon.board.repository.BoardRepository;
import com.soon.board.repository.ImageRepository;
import com.soon.board.repository.UserRepository;
import com.soon.board.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired UserRepository userRepository;
	@Autowired BoardRepository boardRepository;
	@Autowired ImageRepository imageRepository;
	
	@Override
	public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
		try {
			boolean existedEmail = userRepository.existsByEmail(email);
			if (!existedEmail) return PostBoardResponseDto.notExistUser();
			
			BoardEntity boardEntity = new BoardEntity(dto, email);
			boardRepository.save(boardEntity);
			
			int boardNumber = boardEntity.getBoardNumber();
			
			List<String> boardImageList = dto.getBoardImageList();
			List<ImageEntity> imageEntities = new ArrayList<>();
			
			for (String image: boardImageList) {
				ImageEntity imageEntity = new ImageEntity(boardNumber, image);
				imageEntities.add(imageEntity);
			}
			
			imageRepository.saveAll(imageEntities);
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return PostBoardResponseDto.success();
	}

}
