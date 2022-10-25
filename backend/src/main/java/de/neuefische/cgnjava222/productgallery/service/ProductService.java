package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.ImageInfoRepo;
import de.neuefische.cgnjava222.productgallery.ProductRepo;
import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final FileService fileService;
    private final ImageInfoRepo imageInfoRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Optional<Product> getDetailsOf(Long id) {
        return productRepo.findById(id);
    }

    public Product addProduct(NewProduct newProduct) {
        System.out.println(newProduct.getPictureObj());
        var newProd = Product.builder()
                .description(newProduct.getDescription())
                .title(newProduct.getTitle())
                .build();
        var product = productRepo.save(newProd);

        var urls = newProduct.getPictureObj().stream().map(ImageInfo::getImageId).toList();
        var images = imageInfoRepo.findAllById(urls);

        // imageInfoRepo.saveAll(newProduct.getPictureObj());

        var bal = imageInfoRepo.saveAll(
                // newProduct.getPictureObj()
                images.stream().peek(imageInfo -> imageInfo.setProductId(product.getId())).toList()
        );
        System.out.println(bal);
        //    var bla = newProduct.pictureObj().stream().map(
        //            newImage -> ImageInfo.builder()
        //                    .url(newImage.getUrl())
        //                    .publicId(newImage.getPublicId())
        //                    .productId(product.getId())
        //                    .build()).toList();
        //    imageInfoRepo.saveAll(bla);
        System.out.println(product);
        return productRepo.findById(product.getId()).orElseThrow();
    }

    public boolean deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        var publicIdsToDelete = product.getPictureObj().stream().map(ImageInfo::getImageId).toList();
        try {
            fileService.deletePictures(publicIdsToDelete);
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
            return productRepo.save(product);
        }).orElseThrow();
    }

    public void deletePicture(Long imageId) {
        fileService.deletePictures(List.of(imageId));
    }
}
