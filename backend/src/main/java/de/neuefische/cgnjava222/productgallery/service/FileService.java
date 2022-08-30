package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import de.neuefische.cgnjava222.productgallery.exception.FileuploadException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final Cloudinary cloudinary;

    public List<ImageInfo> uploadPictures(MultipartFile[] files) {
        return Arrays.stream(files).map(this::uploadPicture
        ).toList();
    }

    public ImageInfo uploadPicture(MultipartFile file) {
        try {
            if (file.getOriginalFilename() != null) {
                File newFile = File.createTempFile(file.getOriginalFilename(), null);
                file.transferTo(newFile);
                var responseObj = cloudinary.uploader().upload(newFile, ObjectUtils.emptyMap());
                String url = (String) responseObj.get("url");
                String publicID = (String) responseObj.get("public_id");
                return new ImageInfo(url, publicID);
            } else {
                throw new IOException("Filename darf nicht null sein");
            }
        } catch (IOException e) {
            throw new FileuploadException(file.getOriginalFilename());
        }
    }

    public void deletePicture(List<String> id) throws Exception {
        cloudinary.api().deleteResources(id, ObjectUtils.emptyMap());
    }
}
