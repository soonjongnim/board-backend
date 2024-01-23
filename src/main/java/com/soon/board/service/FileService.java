package com.soon.board.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.soon.board.dto.response.file.DeleteFileResponseDto;

public interface FileService {

	String upload(MultipartFile file);
	String cloudUpload(MultipartFile file);
	Resource getImage(String fileName);
	ResponseEntity<? super DeleteFileResponseDto> cloudDelete(Integer boardNumber, String email);
}
