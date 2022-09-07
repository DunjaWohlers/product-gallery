package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import de.neuefische.cgnjava222.productgallery.exception.FileNotDeletedException;
import de.neuefische.cgnjava222.productgallery.exception.FileuploadException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
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
        return Arrays.stream(files).map(
                this::uploadPicture).toList();
    }

    public ImageInfo uploadPicture(MultipartFile file) {
        try {
            if (Strings.isEmpty(file.getOriginalFilename())) {
                throw new IOException();
            }
            File newFile = File.createTempFile(file.getOriginalFilename(), null);
            file.transferTo(newFile);
            var responseObj = cloudinary.uploader().upload(newFile, ObjectUtils.emptyMap());
            String url = (String) responseObj.get("url");
            String publicID = (String) responseObj.get("public_id");
            return new ImageInfo(url, publicID);
        } catch (IOException e) {
            throw new FileuploadException(file.getOriginalFilename());
        }
    }

    public void deletePicture(List<String> ids) {
        try {
            ApiResponse abc = cloudinary.api().deleteResources(ids, ObjectUtils.emptyMap());
            System.out.println(abc);
            Object object = abc.get("deleted");
            //  String deleted = object.get(""+ids[0]);
            System.out.println(object);
            System.out.println(object.getClass());

            if (abc.get("deleted").equals("not_found")) {
                System.out.println("picture nicht gefunden");
            }
        } catch (Exception e) {
            throw new FileNotDeletedException(ids.get(0));
        }
    }
}
