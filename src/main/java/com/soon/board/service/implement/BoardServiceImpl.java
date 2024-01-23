package com.soon.board.service.implement;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soon.board.dto.request.board.PatchBoardRequestDto;
import com.soon.board.dto.request.board.PostBoardRequestDto;
import com.soon.board.dto.request.board.PostCommentRequestDto;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.board.DeleteBoardResponseDto;
import com.soon.board.dto.response.board.GetBoardResponseDto;
import com.soon.board.dto.response.board.GetCommentListResponseDto;
import com.soon.board.dto.response.board.GetFavoriteListResponseDto;
import com.soon.board.dto.response.board.GetLatestBoardListResponseDto;
import com.soon.board.dto.response.board.GetSearchBoardListResponseDto;
import com.soon.board.dto.response.board.GetTop3BoardListResponseDto;
import com.soon.board.dto.response.board.GetUserBoardListResponseDto;
import com.soon.board.dto.response.board.IncreaseViewCountResponseDto;
import com.soon.board.dto.response.board.PatchBoardResponseDto;
import com.soon.board.dto.response.board.PostBoardResponseDto;
import com.soon.board.dto.response.board.PostCommentResponseDto;
import com.soon.board.dto.response.board.PutFavoriteResponseDto;
import com.soon.board.entity.BoardEntity;
import com.soon.board.entity.BoardListViewEntity;
import com.soon.board.entity.CommentEntity;
import com.soon.board.entity.FavoriteEntity;
import com.soon.board.entity.ImageEntity;
import com.soon.board.entity.SearchLogEntity;
import com.soon.board.repository.BoardListViewRepository;
import com.soon.board.repository.BoardRepository;
import com.soon.board.repository.CommentRepository;
import com.soon.board.repository.FavoriteRepository;
import com.soon.board.repository.ImageRepository;
import com.soon.board.repository.SearchLogRepository;
import com.soon.board.repository.UserRepository;
import com.soon.board.repository.resultSet.GetBoardResultSet;
import com.soon.board.repository.resultSet.GetCommentListResultSet;
import com.soon.board.repository.resultSet.GetFavoriteListResultSet;
import com.soon.board.service.BoardService;
import com.soon.board.service.FileService;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired UserRepository userRepository;
	@Autowired BoardRepository boardRepository;
	@Autowired ImageRepository imageRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired FavoriteRepository favoriteRepository;
	@Autowired SearchLogRepository searchLogRepository;
	@Autowired BoardListViewRepository boardListViewRepository;
	@Autowired FileService fileService;
	
	@Override
	public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
		
		GetBoardResultSet resultSet = null;
		List<ImageEntity> imageEntities = new ArrayList<>();
		
		try {
			resultSet = boardRepository.getBoard(boardNumber);
			if (resultSet == null) return GetBoardResponseDto.noExistBoard();
			
			imageEntities = imageRepository.findByBoardNumber(boardNumber);
			
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetBoardResponseDto.success(resultSet, imageEntities);
	}
	
	@Override
	public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {
		
		List<GetFavoriteListResultSet> resultSets = new ArrayList<>();
		
		try {
			boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
			if (!existedBoard) return GetFavoriteListResponseDto.noExistBoard();
			
			resultSets = favoriteRepository.getFavoriteList(boardNumber);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetFavoriteListResponseDto.success(resultSets);
	}
	
	@Override
	public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {
		
		List<GetCommentListResultSet> resultSets = new ArrayList<>();
		
		try {
			boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
			if (!existedBoard) return GetCommentListResponseDto.noExistBoard();
			
			resultSets = commentRepository.getCommentList(boardNumber);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetCommentListResponseDto.success(resultSets);
	}
	
	@Override
	public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {
		
		List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
		
		try {
			boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetLatestBoardListResponseDto.success(boardListViewEntities);
	}
	
	@Override
	public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {
		
		List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
		
		try {
			Date beforeWeek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sevenDaysAgo = simpleDateFormat.format(beforeWeek);
			
			boardListViewEntities = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetTop3BoardListResponseDto.success(boardListViewEntities);
	}
	
	@Override
	public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord,
			String preSearchWord) {
		
		List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
		try {
			boardListViewEntities = boardListViewRepository.findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(searchWord, searchWord);
			
			SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
			searchLogRepository.save(searchLogEntity);
			
			boolean relation = preSearchWord != null;
			if (relation) {
				searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, relation);
				searchLogRepository.save(searchLogEntity);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetSearchBoardListResponseDto.success(boardListViewEntities);
	}
	
	@Override
	public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email) {
		
		List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
		
		try {
			boolean existedUser = userRepository.existsByEmail(email);
			if (!existedUser) return GetUserBoardListResponseDto.notExistUser();
			
			boardListViewEntities = boardListViewRepository.findByWriterEmailOrderByWriteDatetimeDesc(email);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return GetUserBoardListResponseDto.success(boardListViewEntities);
	}
	
	@Override
	public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
		try {
			boolean existedEmail = userRepository.existsByEmail(email);
			if (!existedEmail) return PostBoardResponseDto.noExistUser();
			
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
	
	@Override
	public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email) {
		try {
			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if (boardEntity == null) return PostCommentResponseDto.noExistBoard();
			
			boolean existedUser = userRepository.existsByEmail(email);
			if (!existedUser) return PostCommentResponseDto.notExistUser();
			
			CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
			commentRepository.save(commentEntity);
			
			boardEntity.increaseCommentCount();
			boardRepository.save(boardEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return PostCommentResponseDto.success();
	}

	@Override
	public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
		
		try {
			boolean existedUser = userRepository.existsByEmail(email);
			if (!existedUser) return PutFavoriteResponseDto.noExistUser();
			
			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if (boardEntity == null) return PutFavoriteResponseDto.noExistBoard();
			
			FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
			if (favoriteEntity == null) {
				favoriteEntity = new FavoriteEntity(email, boardNumber);
				favoriteRepository.save(favoriteEntity);
				boardEntity.increaseFavoriteCount();
			} else {
				favoriteRepository.delete(favoriteEntity);
				boardEntity.decreaseFavoriteCount();
			}
			
			boardRepository.save(boardEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return PutFavoriteResponseDto.success();
	}

	@Override
	public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber,
			String email) {
		try {
			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if (boardEntity == null) return PatchBoardResponseDto.noExistBoard();
			
			boolean existedUser = userRepository.existsByEmail(email);
			if (!existedUser) return PatchBoardResponseDto.noExistUser();
			
			String writerEmail = boardEntity.getWriterEmail();
			boolean iswriter = writerEmail.equals(email);
			if (!iswriter) return PatchBoardResponseDto.noPermission();
			
			boardEntity.patchBoard(dto);
			boardRepository.save(boardEntity);
			
			imageRepository.deleteByBoardNumber(boardNumber);
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
		return PatchBoardResponseDto.success();
	}
	
	@Override
	public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {
		try {
			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if (boardEntity == null) return IncreaseViewCountResponseDto.notExistUser();
			
			boardEntity.increaseViewcount();
			boardRepository.save(boardEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return IncreaseViewCountResponseDto.success();
	}

	@Override
	public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {
		try {
			boolean existedUser = userRepository.existsByEmail(email);
			if (!existedUser) return DeleteBoardResponseDto.notExistUser();

			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if (boardEntity == null) return DeleteBoardResponseDto.noExistBoard();
			
			String writerEmail = boardEntity.getWriterEmail();
			boolean isWriter = writerEmail.equals(email);
			if (!isWriter) return DeleteBoardResponseDto.noPermission();
			
			fileService.cloudDelete(boardNumber, email);
			imageRepository.deleteByBoardNumber(boardNumber);
			commentRepository.deleteByBoardNumber(boardNumber);
			favoriteRepository.deleteByBoardNumber(boardNumber);
			
			boardRepository.delete(boardEntity);
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		}
		return DeleteBoardResponseDto.success();
	}

}
