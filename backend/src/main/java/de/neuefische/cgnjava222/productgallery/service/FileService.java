package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.ImageInfoRepo;
import de.neuefische.cgnjava222.productgallery.exception.FileuploadException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final ImageInfoRepo imageInfoRepo;

    public ImageInfo uploadPicture(MultipartFile file, Long productId) {
        try {
            var imageData = file.getBytes();
            var type = file.getContentType();

            return imageInfoRepo.save(
                    ImageInfo
                            .builder()
                            .imageData(imageData)
                            .imageType(type)
                            .productId(productId)
                            .build());

        } catch (IOException e) {
            throw new FileuploadException(file.getOriginalFilename());
        }
    }

    public void deletePicture(Long id) {
        imageInfoRepo.deleteById(id);
    }

    public ImageInfo getPicture(Long imageId) {
        var image = imageInfoRepo.findById(imageId);
        return image.orElseThrow();
    }
}
