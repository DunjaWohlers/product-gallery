package de.neuefische.cgnjava222.productgallery;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.NewProduct;
import de.neuefische.cgnjava222.productgallery.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getProducts() throws Exception {
        mockMvc
                .perform(get("/api/")
                )
                .andExpect(status().isOk());
    }

    @Test
    void getProductPerId() throws Exception {
        String saveResult = mockMvc.perform(post("/api/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Birne"}
                                """)
                )
                .andExpect(status().is(201))
                .andExpect(content().json("""
                        {
                        "title": "Birne"
                        }
                        """))
                .andReturn().getResponse().getContentAsString();
        Product saveResultProduct = objectMapper.readValue(saveResult, Product.class);

        String content = mockMvc.perform(get("/api/details/" + saveResultProduct.id())
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Product actualProduct = objectMapper.readValue(content, Product.class);

        Assertions.assertEquals(actualProduct, saveResultProduct);
    }

    @Test
    void getOneProductNotExisting() throws Exception {
        String notExistingID = "1a";

        mockMvc.perform(get("/api/details/" + notExistingID)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof ProductNotFoundException))
                .andExpect(result -> {
                            if (result.getResolvedException() == null) {
                                fail();
                            } else {
                                assertEquals("Product not Found (id: 1a )",
                                        result.getResolvedException().getMessage());
                            }
                        }
                );
    }

    @DirtiesContext
    @Test
    void addProducts() throws Exception {
        mockMvc.perform(post("/api/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Birne"}
                                """)
                )
                .andExpect(status().is(201))
                .andExpect(content().json("""
                        {
                        "title": "Birne"
                        }
                        """));
    }

    @DirtiesContext
    @Test
    void deleteExistingAndNotExistingProduct() throws Exception {
        String addPromise = mockMvc.perform(post(
                "/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title": "Birne"}
                        """)
        ).andReturn().getResponse().getContentAsString();

        Product addedProductResult = objectMapper.readValue(addPromise, Product.class);
        String id = addedProductResult.id();

        mockMvc.perform(delete("/api/" + id))
                .andExpect(status().is(204));

        String notExistingID = "ABC123";
        mockMvc.perform(delete("/api/" + notExistingID))
                .andExpect(status().is(404));
    }

    @DirtiesContext
    @Test
    void updateProduct() throws Exception {
        String saveResult = mockMvc.perform(post("/api/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Birne"}
                                """)
                )
                .andExpect(status().is(201))
                .andExpect(content().json("""
                        {
                        "title": "Birne"
                        }
                        """))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product saveResultProduct = objectMapper.readValue(saveResult, Product.class);

        NewProduct newProduct = new NewProduct("Product1", "1a Qualität",
                List.of("http://www.bla.de"), 5, 6);
        Product expectedProduct = Product.ProductFactory.create(saveResultProduct.id(), newProduct);
        System.out.println("hi");
        System.out.println(expectedProduct);
        String updateResponse = mockMvc.perform(put("/api/product/" + saveResultProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedProduct))
                )
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualProduct = objectMapper.readValue(updateResponse, Product.class);
        System.out.println("ho");
        System.out.println(actualProduct);
        Assertions.assertEquals(saveResultProduct.id(), actualProduct.id());
        Assertions.assertEquals(expectedProduct, actualProduct);
    }

}
