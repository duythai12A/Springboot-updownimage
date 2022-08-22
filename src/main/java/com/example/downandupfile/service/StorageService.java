package com.example.downandupfile.service;


import com.example.downandupfile.entity.ImageData;
import com.example.downandupfile.reponsitory.StorageRepository;
import com.example.downandupfile.until.ImageUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private StorageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUntils.compressImage(file.getBytes())).build());
        if(imageData!=null){
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }
    public byte[] downloadImage(String fileName){
       Optional<ImageData> dbImageData = repository.findByName(fileName);
       byte[] iamges = ImageUntils.decompressImage(dbImageData.get().getImageData());
       return iamges;
    }
}
