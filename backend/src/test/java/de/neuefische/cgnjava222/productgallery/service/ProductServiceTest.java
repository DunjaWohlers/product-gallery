package de.neuefische.cgnjava222.productgallery.service;

import com.cloudinary.Cloudinary;
import de.neuefische.cgnjava222.productgallery.ProductRepo;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.model.ProductReducedInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    Product product1 = new Product("1", "Biber", "knuffig, flauschig",
            List.of(new ImageInfo("http://google.de", "publicID2")), 4, 5);
    Product product2 = new Product("2", "Pferd", "braun, holzig",
            List.of(new ImageInfo("http://google.de", "publicID2")), 4, 5);
    Product product3 = new Product("3", "Brett", "Frühstücksbrett, Schneidebrett",
            List.of(new ImageInfo("http://google.de", "publicID2")), 4, 5);
    NewProduct newProduct3 = new NewProduct("Brett", "Frühstücksbrett, Schneidebrett",
            List.of(new ImageInfo("http://google.de", "publicID2")), 4, 5);

    @Test
    void getProducts() {
        List<Product> productsFromRepo = List.of(product1, product2, product3);
        ProductRepo productRepo = mock(ProductRepo.class);
        when(productRepo.findAll()).thenReturn(productsFromRepo);

        List<ProductReducedInfo> expectedProducts = productsFromRepo.stream()
                .map(element -> new ProductReducedInfo(element.id(), element.title(), element.pictureObj().get(0).url(), element.price()))
                .toList();
        ProductService productService = new ProductService(productRepo, new FileService(new Cloudinary()));
        List<ProductReducedInfo> actualProducts = productService.getAllProducts();

        assertThat(actualProducts).hasSameElementsAs(expectedProducts);
    }

    @Test
    void getProductPerId() {
        Product expectedProduct = product1;
        ProductRepo productRepo = mock(ProductRepo.class);
        when(productRepo.findById(expectedProduct.id())).thenReturn(Optional.of(expectedProduct));

        ProductService productService = new ProductService(productRepo, new FileService(new Cloudinary()));
        Product actualProduct = productService.getDetailsOf(expectedProduct.id()).orElseThrow(
                () -> new RuntimeException("Details vom Produkt mit der id " + expectedProduct.id() + " nicht gefunden!")
        );
        assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    @Test
    void addProductTest() {
        Product expected = product1;
        ProductRepo productRepo = mock(ProductRepo.class);
        when(productRepo.save(expected)).thenReturn(expected);
        Product actual = productRepo.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteProductTest() {
        Product expected = product3;
        ProductRepo productRepo = mock(ProductRepo.class);

        when(productRepo.existsById(expected.id())).thenReturn(true);
        doNothing().when(productRepo).deleteById(expected.id());

        productRepo.deleteById(expected.id());
        verify(productRepo).deleteById(expected.id());
    }

    @Test
    void updateProductTest() {
        Product expected = product3;
        ProductRepo productRepo = mock(ProductRepo.class);
        when(productRepo.save(product3)).thenReturn(product3);

        ProductService productService = new ProductService(productRepo, new FileService(new Cloudinary()));
        Product actual = productService.updateProduct(expected.id(), newProduct3);
        Assertions.assertEquals(expected, actual);
    }
}
