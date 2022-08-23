package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.ProductRepo;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.model.ProductListType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public List<ProductListType> getAllProducts() {
        return productRepo.findAll().stream().map(element ->
                new ProductListType(element.id(), element.title(), element.pictureUrls().get(0), element.price())
        ).toList();
    }

    public Optional<Product> getDetailsOf(String id) {
        return productRepo.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public boolean deleteProduct(String id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Product updateProduct(String id, NewProduct newProduct) {
        return productRepo.save(Product.ProductFactory.create(id, newProduct));
    }
}
