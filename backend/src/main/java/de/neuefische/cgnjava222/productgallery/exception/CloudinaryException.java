package de.neuefische.cgnjava222.productgallery.exception;

public class CloudinaryException extends RuntimeException {
    public CloudinaryException() {
        super("Cloudinary hat eine unbekannte Exception geworfen.");
    }
}
