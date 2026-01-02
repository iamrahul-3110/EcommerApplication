package com.ecom.project.ecommerceapp.product.service.impl;

import com.ecom.project.ecommerceapp.product.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        // File names of current/original image
        String originalFilename = image.getOriginalFilename();

        // Generate unique file name logic can be added here using random UUIDs or timestamps
        // mat.jpg ---> 1234-5678-8901-3456.jpg kind of format
        assert originalFilename != null;
        String randomFilename = (UUID.randomUUID().toString()).concat(originalFilename.substring(originalFilename.lastIndexOf('.'))); // randomId.jpg
        String filePath = path + File.separator + randomFilename; // example: images/1234-5678-8901-3456.jpg

        // Check if path exists, if not create it
        File folder = new File(path);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        // Upload to server
        Files.copy(image.getInputStream(), Paths.get(filePath));

        return randomFilename;
    }
}
