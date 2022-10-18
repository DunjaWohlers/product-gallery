package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.ProductRepo;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    Product product1 = new Product("1", "Biber", "knuffig, flauschig",
            List.of(new ImageInfo("http://google.de", "publicID2")));
    Product product2 = new Product("2", "Pferd", "braun, holzig",
            List.of(new ImageInfo("http://google.de", "publicID2")));
    Product product3 = new Product("3", "Brett", "Frühstücksbrett, Schneidebrett",
            List.of(new ImageInfo("http://google.de", "publicID2")));


    @Test
    void getProducts() {
        List<Product> expectedProducts = List.of(product1, product2, product3);
        ProductRepo productRepo = mock(ProductRepo.class);
        when(productRepo.findAll()).thenReturn(expectedProducts);

        ProductService productService = new ProductService(productRepo);
        List<Product> actualProducts = productService.getAllProducts();

        assertThat(actualProducts).hasSameElementsAs(expectedProducts);
    }

    @Test
    void getProductPerId() {
        Product expectedProduct = product1;
        ProductRepo productRepo = mock(ProductRepo.class);
        when(productRepo.findById(expectedProduct.id())).thenReturn(Optional.of(expectedProduct));

        ProductService productService = new ProductService(productRepo);
        Product actualProduct = productService.getDetailsOf(expectedProduct.id()).orElseThrow(
                () -> new RuntimeException("Details vom Produkt mit der id " + expectedProduct.id() + " nicht gefunden!")
        );
        assertThat(actualProduct).isEqualTo(expectedProduct);
    }
}
