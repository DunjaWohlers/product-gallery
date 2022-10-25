package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import de.neuefische.cgnjava222.productgallery.ImageInfoRepo;
import de.neuefische.cgnjava222.productgallery.exception.FileuploadException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final Cloudinary cloudinary;
    private final ImageInfoRepo imageInfoRepo;

    public List<ImageInfo> uploadPictures(MultipartFile[] files) {
        return Arrays.stream(files).map(
                this::uploadPicture).toList();
    }

    public ImageInfo uploadPicture(MultipartFile file) {
        try {
            var imageData = file.getBytes();
            var type = file.getContentType();

            var imageInfo = imageInfoRepo.save(
                    ImageInfo
                            .builder()
                            .imageData(imageData)
                            .imageType(type)
                            .build());
            return imageInfo;

        } catch (IOException e) {
            throw new FileuploadException(file.getOriginalFilename());
        }
    }

    public void deletePictures(List<Long> ids) {
        imageInfoRepo.deleteAllById(ids);
    }

    public ImageInfo getPicture(Long imageId) {
        var image = imageInfoRepo.findById(imageId);
        return image.orElseThrow();
    }
}
