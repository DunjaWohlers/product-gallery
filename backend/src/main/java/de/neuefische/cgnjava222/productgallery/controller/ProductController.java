package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/details/{id}")
    public Product getDetailsPerId(@PathVariable String id) {
        return productService.getDetailsOf(id).orElseThrow(() ->
                new ProductNotFoundException(id));
    }
}
