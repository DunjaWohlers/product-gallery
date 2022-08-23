package de.neuefische.cgnjava222.productgallery.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String id) {
        super("Product not Found (id: " + id + " )");
    }
}
