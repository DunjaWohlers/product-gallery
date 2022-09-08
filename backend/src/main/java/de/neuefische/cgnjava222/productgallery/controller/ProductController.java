package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.model.ProductReducedInfo;
import de.neuefische.cgnjava222.productgallery.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<ProductReducedInfo> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/details/{id}")
    public Product getDetailsPerId(@PathVariable String id) {
        return productService.getDetailsOf(id).orElseThrow(() ->
                new ProductNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product addProduct(@RequestBody NewProduct newProduct) {
        return productService.addProduct(
                Product.ProductFactory.create(newProduct)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean deleteSuccess = productService.deleteProduct(id);
        return new ResponseEntity<>(deleteSuccess ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Product updateProduct(@PathVariable String id, @RequestBody NewProduct newProduct) {
        return productService.updateProduct(id, newProduct);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/{publicImageId}")
    public void deleteImageFromProductWithId(
            @PathVariable String id,
            @PathVariable String publicImageId) {
        productService.deletePictureFromProduct(publicImageId, id);
    }
}
