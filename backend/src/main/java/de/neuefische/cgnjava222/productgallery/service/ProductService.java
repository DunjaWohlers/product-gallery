package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.ProductRepo;
import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.model.ProductReducedInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final FileService fileService;

    public List<ProductReducedInfo> getAllProducts() {
        return productRepo.findAll().stream().map(product -> new ProductReducedInfo(product.id(), product.title(), product.pictureObj().get(0).url(), product.price())).toList();
    }

    public Optional<Product> getDetailsOf(String id) {
        return productRepo.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public boolean deleteProduct(String id) {
        if (productRepo.existsById(id)) {
            Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
            List<String> publicIdsToDelete = product.pictureObj().stream().map(ImageInfo::public_id).toList();
            try {
                fileService.deletePicture(publicIdsToDelete);
            } catch (Exception e) {
                return false;
            }
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Product updateProduct(String id, NewProduct newProduct) {
        return productRepo.save(Product.ProductFactory.create(id, newProduct));
    }

    public boolean deletePictureFromProduct(String picturePublicId, String productId) {
        try {
            fileService.deletePicture(List.of(picturePublicId));
            Product product = productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
            List<ImageInfo> picObjects = product.pictureObj();
            List<ImageInfo> newpicObjects = picObjects.stream().filter(element -> !element.public_id().equals(picturePublicId)).toList();
            Product newProduct = new Product(
                    product.id(),
                    product.title(),
                    product.description(),
                    newpicObjects,
                    product.price(),
                    product.availableCount()
            );
            productRepo.save(newProduct);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
