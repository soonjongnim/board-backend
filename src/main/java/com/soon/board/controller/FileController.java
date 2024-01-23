package com.soon.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soon.board.dto.response.file.DeleteFileResponseDto;
import com.soon.board.service.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController {

	@Autowired FileService fileService;
	
	@PostMapping("/upload")
	public String upload(HttpServletRequest req, @RequestParam("file") MultipartFile file) {
//		String url = fileService.upload(file);
		String url = fileService.cloudUpload(file);
		return url;
	}
	
	@GetMapping(value="{fileName}", produces={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public Resource getImage(@PathVariable("fileName") String fileName) {
		Resource resource = fileService.getImage(fileName);
		return resource;
	}
	
	@DeleteMapping("/delete/{boardNumber}")
	public ResponseEntity<? super DeleteFileResponseDto> cloudDelete(@PathVariable("boardNumber") Integer boardNumber, 
			@AuthenticationPrincipal String email) {
		ResponseEntity<? super DeleteFileResponseDto> response = fileService.cloudDelete(boardNumber, email);
		return response;
	}
}
