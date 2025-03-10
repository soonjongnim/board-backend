package com.soon.board.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.soon.board.dto.response.file.DeleteFileResponseDto;

public interface FileService {

	String upload(MultipartFile file);
	String cloudUpload(MultipartFile file);
	Resource getImage(String fileName);
	ResponseEntity<? super DeleteFileResponseDto> cloudDelete(Integer number, String email, String type);
	ResponseEntity<? super DeleteFileResponseDto> adminCloudImagesDelete(Integer itemId, List<String> delImageList);
	ResponseEntity<? super DeleteFileResponseDto> adminCloudThumbnailsDelete(Integer itemId, List<String> delThumbnailList);
}
