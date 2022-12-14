package com.epicode.undercontrol.image;

import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageDataService {
	 @Autowired
	 private ImageDataRepository repository;
	 @Transactional
	 public String uploadImage(MultipartFile file) throws IOException {

	        ImageData imageData = repository.save(ImageData.builder()
	                .name(file.getOriginalFilename())
	                .type(file.getContentType())
	                .imageData(ImageUtils.compressImage(file.getBytes())).build());
	        if (imageData != null) {
	            return "file uploaded successfully : " + file.getOriginalFilename();
	        }
	        return null;
	    }

	 @Transactional
	    public byte[] downloadImage(String fileName){
	        Optional<ImageData> dbImageData = repository.findByName(fileName);
	        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
	        return images;
	    }
	 
}
