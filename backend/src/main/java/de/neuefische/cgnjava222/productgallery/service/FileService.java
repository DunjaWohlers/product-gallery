package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {
    private final Cloudinary cloudinary;

    public FileService() {
        this.cloudinary = new Cloudinary();
    }

    public ResponseEntity<String> uploadPicture(MultipartFile file) throws IOException {
        File newFile = File.createTempFile(file.getOriginalFilename(), null);
        file.transferTo(newFile);
        String url = cloudinary.uploader().upload(newFile, ObjectUtils.emptyMap()).get("url").toString();
        return new ResponseEntity<>(url, HttpStatus.CREATED);
    }
}
