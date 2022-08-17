package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    String urlStart = "https://res.cloudinary.com/dcnqizhmg/image/upload";
    Product shopEntry1 = new Product("3a", "Roll-Brett",
            "Aus Holz, Massivholz",
            List.of(urlStart + "/v1660381069/hk39bkxodeg6kopmht9d.png"), 12, 1);

    public List<Product> getAllProducts() {
        return List.of(shopEntry1);
    }
}
