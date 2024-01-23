package com.soon.board.service.implement;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.bmc.Region;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import com.soon.board.dto.response.ResponseDto;
import com.soon.board.dto.response.file.DeleteFileResponseDto;
import com.soon.board.entity.ImageEntity;
import com.soon.board.provider.AuthentificationProvider;
import com.soon.board.repository.ImageRepository;
import com.soon.board.repository.UserRepository;
import com.soon.board.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Value("${file.path}")
	private String filePath;
	@Value("${file.url}")
	private String fileUrl;
	@Value("${oci.namespaceName}")
	private String namespaceName;
	@Value("${oci.bucketName}")
	private String bucketName;
	
	@Autowired AuthentificationProvider authentificationProvider;
	@Autowired UserRepository userRepository;
	@Autowired ImageRepository imageRepository;
	
	@Override
	public String upload(MultipartFile file) {
		
		if (file.isEmpty()) return null;
		
		String originalFileName = file.getOriginalFilename();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String uuid = UUID.randomUUID().toString();
		String saveFileName = uuid + extension;
		String savePath = filePath + saveFileName;
		
		try {
			file.transferTo(new File(savePath));
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		
		String url = fileUrl + saveFileName;
		return url;
	}

	@Override
	public String cloudUpload(MultipartFile file) {
		
		if (file.isEmpty()) return null;
        
		String originalFileName = file.getOriginalFilename();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String uuid = UUID.randomUUID().toString();
		String saveFileName = uuid + extension;
		
		Map<String, String> metadata = null;
		String contentType = file.getContentType();
		String contentEncoding = null;
		String contentLanguage = null;
		
		ObjectStorageClient client = null;
		try {
			
			InputStream body = file.getInputStream();
			
			client = new ObjectStorageClient(authentificationProvider.getAuthenticationDetailsProvider());
	        client.setRegion(Region.AP_CHUNCHEON_1);

	        PutObjectRequest request =
	                PutObjectRequest.builder()
	                        .bucketName(bucketName)
	                        .namespaceName(namespaceName)
	                        .objectName(saveFileName)
	                        .contentType(contentType)
	                        .contentLanguage(contentLanguage)
	                        .contentEncoding(contentEncoding)
	                        .opcMeta(metadata)
	                        .putObjectBody(body)
	                        .build();

	        PutObjectResponse response = client.putObject(request);

	        // Print the ETag of the uploaded object
	        System.out.println("ETag: " + response.getETag());
	        
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		} finally {
			client.close();
		}
		
		String url = fileUrl + saveFileName;
		return url;
	}
	
	@Override
	public Resource getImage(String fileName) {
		
		Resource resource = null;
		
		try {
			System.out.println("FileService.getImage");
			resource = new UrlResource("file:" + filePath + fileName);  // localhost용
//			resource = new UrlResource(filePath + fileName);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return resource;
	}
	
	@Override
	public ResponseEntity<? super DeleteFileResponseDto> cloudDelete(Integer boardNumber, String email) {
		
		boolean existedUser = userRepository.existsByEmail(email);
		if (!existedUser) return DeleteFileResponseDto.notExistUser();
		
		ObjectStorageClient client = null;
		try {
			
			client = new ObjectStorageClient(authentificationProvider.getAuthenticationDetailsProvider());
	        client.setRegion(Region.AP_CHUNCHEON_1);
	        
	        List<ImageEntity> imageEntities = imageRepository.findByBoardNumber(boardNumber);
			if (imageEntities == null) return DeleteFileResponseDto.notExistImages();
			
			for (ImageEntity imageEntity: imageEntities) {
				String image = imageEntity.getImage();
		        URI uri = URI.create(image);
		        Path path = FileSystems.getDefault().getPath(uri.getPath());
		        String objectName = path.getFileName().toString();
				System.out.println("image: " + image);
				System.out.println("objectName: " + objectName);
				deleteObject(client, objectName);
			}
	        
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseDto.databaseError();
		} finally {
			client.close();
		}
		
		return DeleteFileResponseDto.success();
	}
	
	private void deleteObject(ObjectStorage client, String objectName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucketName(bucketName)
                .namespaceName(namespaceName)
                .objectName(objectName)
                .build();

        client.deleteObject(deleteObjectRequest);

        System.out.println("Object deleted successfully");
    }

}
