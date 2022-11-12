package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.ProductRepo;
import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final FileService fileService;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product addProduct(NewProduct newProduct) {
      //  System.out.println(newProduct.getPictureObj());
        var newProd = Product.builder()
                .description(newProduct.getDescription())
                .title(newProduct.getTitle())
                .position(newProduct.getPosition())
                .build();
        var product = productRepo.save(newProd);

        return productRepo.findById(product.getId()).orElseThrow();
    }

    public boolean deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        var publicIdsToDelete = product.getPictureObj().stream().map(ImageInfo::getImageId).toList();
        try {
            publicIdsToDelete.forEach(fileService::deletePicture);
        } catch (Exception e) {
            return false;
        }
        productRepo.deleteById(id);
        return true;
    }

    public Product updateProduct(Long id, NewProduct newProduct) {
        return productRepo.findById(id).map(product -> {
            product.setTitle(newProduct.getTitle());
            product.setDescription(newProduct.getDescription());
            product.setPosition(newProduct.getPosition());
            return productRepo.save(product);
        }).orElseThrow();
    }

}
