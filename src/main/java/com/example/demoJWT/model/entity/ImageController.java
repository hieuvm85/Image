package com.example.demoJWT.model.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import net.coobird.thumbnailator.Thumbnails;

@RestController
@RequestMapping("/api/image")
public class ImageController {

	@Autowired
	ImageRepository imageRepository;
	@Autowired
	 private ImageService imageService;

	@PostMapping("/addImage")
	public ResponseEntity<String> uploadImage(@RequestBody byte[] imageData) throws IOException {
		Image image = new Image();
		image.setImageBytes(imageData);
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int width = 300; // Đặt kích thước mới của chiều rộng
        int height = 360; // Đặt kích thước mới của chiều cao
        Thumbnails.of(inputStream)
        	.size(width, height)
        	.outputFormat("jpg") // Định dạng đầu ra
        	.toOutputStream(outputStream);
		
		image.setImageBytes(outputStream.toByteArray());
		imageRepository.save(image);
        return new ResponseEntity<>("/api/image/get?id="+image.getId(), HttpStatus.OK);
    }
	
	 @GetMapping("/get")
	    public ResponseEntity<byte[]> getImageById(@RequestParam("id") int id) {
	        byte[] imageData = imageService.getImageById(id);

	        if (imageData != null) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.IMAGE_JPEG); // Đặt kiểu content là ảnh JPEG, bạn có thể điều chỉnh theo định dạng ảnh bạn đang lưu trữ.

	            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	   
}
