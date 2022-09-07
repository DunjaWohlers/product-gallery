package de.neuefische.cgnjava222.productgallery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException exception) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", "Product not Found ");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FileuploadException.class)
    public ResponseEntity<Object> handleFileNotUploaded(FileuploadException exception) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", "File not Updated ");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FileNotDeletedException.class)
    public ResponseEntity<Object> handleFileNotDeleted(FileNotDeletedException exception) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", "File not deleted ");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CloudinaryException.class)
    public ResponseEntity<Object> handleCloudinaryException(CloudinaryException exception) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", "Cloudinary Exception ");
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


}
