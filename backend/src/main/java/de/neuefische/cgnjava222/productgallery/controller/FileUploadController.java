package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")

public class FileUploadController {
    private final FileService fileService;

    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile/{productId}")
    public ResponseEntity<ImageInfo> addPicture(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file
    ) {
        return new ResponseEntity<>(fileService.uploadPicture(file, productId), HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getPicture(@PathVariable Long imageId) {
        var imageInfo = fileService.getPicture(imageId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(imageInfo.getImageType()))
                .body(imageInfo.getImageData());
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<Void> deletePicture(@PathVariable Long imageId) {
        try {
            fileService.deletePicture(imageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
