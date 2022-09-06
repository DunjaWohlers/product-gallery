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
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        List<String> publicIdsToDelete = product.pictureObj().stream().map(ImageInfo::publicId).toList();
        try {
            fileService.deletePicture(publicIdsToDelete);
        } catch (Exception e) {
            return false;
        }
        productRepo.deleteById(id);
        return true;
    }

    public Product updateProduct(String id, NewProduct newProduct) {
        return productRepo.save(Product.ProductFactory.create(id, newProduct));
    }

    public void deletePictureFromProduct(String picturePublicId, String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        List<ImageInfo> picObjects = product.pictureObj();
        List<ImageInfo> newPicObjects = picObjects.stream().filter(element -> !element.publicId().equals(picturePublicId)).toList();
        Product actualProduct = new Product(
                product.id(),
                product.title(),
                product.description(),
                newPicObjects,
                product.price(),
                product.availableCount()
        );
        fileService.deletePicture(List.of(picturePublicId));
        productRepo.save(actualProduct);
    }
}
