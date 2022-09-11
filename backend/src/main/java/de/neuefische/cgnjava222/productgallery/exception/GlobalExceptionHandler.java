package de.neuefische.cgnjava222.productgallery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException exception) {
        return getResponseEntity("Product not Found ", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FileuploadException.class)
    public ResponseEntity<Object> handleFileNotUploaded(FileuploadException exception) {
        return getResponseEntity("File not Updated ", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FileNotDeletedException.class)
    public ResponseEntity<Object> handleFileNotDeleted(FileNotDeletedException exception) {
        return getResponseEntity("File not deleted ", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CloudinaryException.class)
    public ResponseEntity<Object> handleCloudinaryException(CloudinaryException exception) {
        return getResponseEntity("Cloudinary Exception ", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> getResponseEntity(String msg, HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", msg);
        return new ResponseEntity<>(responseBody, status);
    }
}
