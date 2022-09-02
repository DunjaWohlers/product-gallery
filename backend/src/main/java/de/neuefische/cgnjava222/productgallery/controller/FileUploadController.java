package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class FileUploadController {
    private final FileService fileService;

    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<List<ImageInfo>> addPicture(@RequestParam("file") MultipartFile[] files) {
        return new ResponseEntity<>(fileService.uploadPictures(files), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{publicID}")
    public ResponseEntity<Void> deletePicture(@PathVariable String publicID) {
        try {
            fileService.deletePicture(List.of(publicID));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
