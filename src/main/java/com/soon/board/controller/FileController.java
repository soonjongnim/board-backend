package com.soon.board.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon.board.dto.response.file.DeleteFileResponseDto;
import com.soon.board.service.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController {

	@Autowired FileService fileService;
	
	@PostMapping("/upload")
	public String upload(HttpServletRequest req, @RequestParam("file") MultipartFile file) {
//		String url = fileService.upload(file);
		System.out.println("file: " + file);
		String url = fileService.cloudUpload(file);
		return url;
	}
	
	@GetMapping(value="{fileName}", produces={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public Resource getImage(@PathVariable("fileName") String fileName) {
		Resource resource = fileService.getImage(fileName);
		return resource;
	}
	
	@DeleteMapping("/delete/{number}/{type}")
	public ResponseEntity<? super DeleteFileResponseDto> cloudDelete(@PathVariable("number") Integer number, 
			@AuthenticationPrincipal String email, @PathVariable("type") String type) {
		ResponseEntity<? super DeleteFileResponseDto> response = fileService.cloudDelete(number, email, type);
		return response;
	}
	
	@DeleteMapping("/admin/cloudImagesDelete/{itemId}")
	public ResponseEntity<? super DeleteFileResponseDto> adminCloudImagesDelete(@PathVariable("itemId") Integer itemId, @RequestBody String deletedImageList) throws JsonMappingException, JsonProcessingException {
		System.out.println("adminCloudImagesDelete");
		System.out.println("itemId: " + itemId);
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> urlList = null;
        try {
            // Convert JSON string to List<String>
            urlList = objectMapper.readValue(deletedImageList, new TypeReference<List<String>>() {});
            System.out.println("Converted List: " + urlList);
        } catch (Exception e) {
            e.printStackTrace();
        }
		ResponseEntity<? super DeleteFileResponseDto> response = fileService.adminCloudImagesDelete(itemId, urlList);
		return response;
	}
	
	@DeleteMapping("/admin/cloudThumbnailsDelete/{itemId}")
	public ResponseEntity<? super DeleteFileResponseDto> adminCloudThumbnailsDelete(@PathVariable("itemId") Integer itemId, @RequestBody String deletedThumbnailList) throws JsonMappingException, JsonProcessingException {
		System.out.println("adminCloudThumbnailsDelete");
		System.out.println("itemId: " + itemId);
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> urlList = null;
        try {
            // Convert JSON string to List<String>
            urlList = objectMapper.readValue(deletedThumbnailList, new TypeReference<List<String>>() {});
            System.out.println("Converted List: " + urlList);
        } catch (Exception e) {
            e.printStackTrace();
        }
		ResponseEntity<? super DeleteFileResponseDto> response = fileService.adminCloudThumbnailsDelete(itemId, urlList);
		return response;
	}
}
