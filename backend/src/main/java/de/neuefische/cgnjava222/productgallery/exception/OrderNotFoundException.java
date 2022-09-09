package de.neuefische.cgnjava222.productgallery.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String userName) {
        super("Order not Found from user " + userName);
    }
}
