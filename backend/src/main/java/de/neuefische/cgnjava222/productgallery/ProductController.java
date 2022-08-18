package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(
                new Product(product.title(), product.description(),
                        product.pictureUrls(), product.price(), product.availableCount())
        );
    }
}
