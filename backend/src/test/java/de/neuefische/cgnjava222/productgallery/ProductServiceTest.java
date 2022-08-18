package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.service.ProductService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Test
    void getProducts() {
        List<Product> expectedProducts = List.of(
                new Product("1", "Biber", "knuffig, flauschig", List.of("http://google.de"), 4, 5),
                new Product("2", "Pferd", "braun, holzig", List.of("http://google.de"), 4, 5),
                new Product("3", "Brett", "Frühstücksbrett, Schneidebrett", List.of("http://google.de"), 4, 5)
        );

        ProductRepo productRepo = mock(ProductRepo.class);
        when(productRepo.findAll()).thenReturn(expectedProducts);

        ProductService productService = new ProductService(productRepo);
        List<Product> actualProducts = productService.getAllProducts();

        assertThat(actualProducts).hasSameElementsAs(expectedProducts);
    }
}
