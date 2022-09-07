package de.neuefische.cgnjava222.productgallery.exception;

public class CloudinaryException extends Throwable {
    public CloudinaryException() {
        super("Cloudinary hat eine unbekannte Exception geworfen.");
    }
}
