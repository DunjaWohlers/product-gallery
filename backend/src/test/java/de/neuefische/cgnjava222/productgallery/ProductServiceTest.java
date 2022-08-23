package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.model.ProductListType;
import de.neuefische.cgnjava222.productgallery.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    ProductListType productListType1 = new ProductListType("ff2", "Brot123 mit Sahne", "ggg.bb.cu", 4);
    ProductListType productListType2 = new ProductListType("ff3", "Brot13 ohne Brot", "omw.na.gj", 1000);

    Product product1 = new Product("1", "Biber", "knuffig, flauschig",
            List.of("http://google.de"), 4, 5);
    Product product2 = new Product("2", "Pferd", "braun, holzig",
            List.of("http://google.de"), 4, 5);
    Product product3 = new Product("3", "Brett", "Frühstücksbrett, Schneidebrett",
            List.of("http://google.de"), 4, 5);

    NewProduct newProduct3 = new NewProduct("Brett", "Frühstücksbrett, Schneidebrett",
            List.of("http://google.de"), 4, 5);

    @Test
    void getProducts() {
        List<Product> productsFromRepo = List.of(product1, product2, product3);
        ProductRepo productRepo = mock(ProductRepo.class);
        when(productRepo.findAll()).thenReturn(productsFromRepo);

        ProductListType prod1 = new ProductListType(product1.id(), product1.title(), product1.pictureUrls().get(0), product1.price());
        ProductListType prod2 = new ProductListType(product2.id(), product2.title(), product2.pictureUrls().get(0), product2.price());
        ProductListType prod3 = new ProductListType(product3.id(), product3.title(), product3.pictureUrls().get(0), product3.price());

        List<ProductListType> expectedProducts = List.of(prod1, prod2, prod3);
        ProductService productService = new ProductService(productRepo);
        List<ProductListType> actualProducts = productService.getAllProducts();

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

        ProductService productService = new ProductService(productRepo);
        Product actual = productService.updateProduct(expected.id(), newProduct3);
        Assertions.assertEquals(expected, actual);
    }
}
