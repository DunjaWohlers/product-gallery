package de.neuefische.cgnjava222.productgallery.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void nonCanonicalArgsConstructorCreatesID() {
        Product product = new Product("Biber", "knuffig, flauschig", List.of("http://google.de"), 4, 5);
        String productID = product.id();
        Integer expected = 36;
        Integer actual = productID.length();
        assertThat(actual).isEqualTo(expected);
    }
}
