package de.neuefische.cgnjava222.productgallery;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ProductRepo productRepo;

    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get("/api/products/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getProductPerId() throws Exception {
        NewProduct newProduct = new NewProduct("ABC", "def", List.of(new ImageInfo("asd", "asd")));
        String id = UUID.randomUUID().toString();
        Product product = Product.ProductFactory.create(id, newProduct);
        productRepo.save(product);

        String content = mockMvc.perform(
                        get("/api/products/details/" + id)
                )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Product actualProduct = objectMapper.readValue(content, Product.class);

        assertEquals(actualProduct, product);
    }

    @Test
    void getOneProductNotExisting() throws Exception {
        String notExistingID = "1a";

        mockMvc.perform(
                        get("/api/products/details/" + notExistingID)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound()).andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException)).andExpect(result -> {
                    if (result.getResolvedException() == null) {
                        fail();
                    } else {
                        assertEquals("Product not Found (id: 1a )", result.getResolvedException().getMessage());
                    }
                });
    }

}
