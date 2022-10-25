package de.neuefische.cgnjava222.productgallery.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Product not Found (id: " + id + " )");
    }
}
