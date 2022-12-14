package de.neuefische.cgnjava222.productgallery.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void nonCanonicalArgsConstructorCreatesID() {
        NewProduct newProduct = new NewProduct("Biber", "knuffig, flauschig",
                List.of(new ImageInfo("http://google.de", "publicID7")), 4, 5);
        Product product = Product.ProductFactory.create(newProduct);
        String productID = product.id();
        Integer expected = 36;
        Integer actual = productID.length();
        assertThat(actual).isEqualTo(expected);
    }
}
