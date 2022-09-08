package de.neuefische.cgnjava222.productgallery.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String name) {
        super("User " + name + " wurde nicht gefunden");
    }
}
