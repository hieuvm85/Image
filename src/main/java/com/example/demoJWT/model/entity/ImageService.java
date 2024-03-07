package com.example.demoJWT.model.entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public byte[] getImageById(int id) {
        return imageRepository.findById(id)
                .map(Image::getImageBytes)
                .orElse(null);
    }
}