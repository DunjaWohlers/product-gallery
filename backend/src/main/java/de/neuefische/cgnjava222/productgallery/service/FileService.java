package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import de.neuefische.cgnjava222.productgallery.exception.CloudinaryException;
import de.neuefische.cgnjava222.productgallery.exception.FileNotDeletedException;
import de.neuefische.cgnjava222.productgallery.exception.FileuploadException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService {
    private final Cloudinary cloudinary;

    public List<ImageInfo> uploadPictures(MultipartFile[] files) {
        return Arrays.stream(files).map(
                this::uploadPicture).toList();
    }

    public ImageInfo uploadPicture(MultipartFile file) {
        try {
            File newFile = File.createTempFile(Objects.requireNonNull(file.getOriginalFilename()), null);
            file.transferTo(newFile);
            var responseObj = cloudinary.uploader().upload(newFile, ObjectUtils.emptyMap());
            String url = (String) responseObj.get("url");
            String publicID = (String) responseObj.get("public_id");
            return new ImageInfo(url, publicID);
        } catch (IOException e) {
            throw new FileuploadException(file.getOriginalFilename());
        }
    }

    public void deletePictures(List<String> ids) {
        ApiResponse cloudinaryResponse = tryDeleteResource(ids);
        Map<String, String> object = (Map) cloudinaryResponse.get("deleted");
        String deleted = object.get(ids.get(0));
        if (deleted.equals("not_found")) {
            throw new FileNotDeletedException(ids.get(0));
        }
    }

    private ApiResponse tryDeleteResource(List<String> ids) {
        try {
            return cloudinary.api().deleteResources(ids, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new CloudinaryException();
        }
    }
}
