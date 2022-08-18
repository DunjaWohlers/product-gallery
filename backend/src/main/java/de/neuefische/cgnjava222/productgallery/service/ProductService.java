package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.ProductRepo;
import de.neuefische.cgnjava222.productgallery.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }
}
