package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        String urlStart = "https://res.cloudinary.com/dcnqizhmg/image/upload";
        Product shopEntry1 = new Product("3a", "Roll-Brett",
                "Aus Holz, Massivholz",
                List.of(urlStart + "/v1660381069/hk39bkxodeg6kopmht9d.png"), 12, 1);
        //  productRepo.save(shopEntry1);
        return List.of(shopEntry1);
    }
}
