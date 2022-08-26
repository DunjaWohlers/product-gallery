package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileService {
    private final Cloudinary cloudinary;

    public ImageInfo uploadPicture(MultipartFile file) throws IOException {
        if (file.getOriginalFilename() != null) {
            File newFile = File.createTempFile(file.getOriginalFilename(), null);
            file.transferTo(newFile);
            Map<String, String> responseObj = cloudinary.uploader().upload(newFile, ObjectUtils.emptyMap());
            String url = responseObj.get("url");
            String publicID = responseObj.get("public_id");
            return new ImageInfo(url, publicID);
        } else {
            throw new IOException("Filename darf nicht null sein");
        }
    }

    public void deletePicture(List<String> id) throws Exception {
        cloudinary.api().deleteResources(id, ObjectUtils.emptyMap());
    }
}
