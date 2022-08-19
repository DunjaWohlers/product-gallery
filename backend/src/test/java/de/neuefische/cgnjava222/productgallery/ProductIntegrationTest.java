package de.neuefische.cgnjava222.productgallery;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.cgnjava222.productgallery.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

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

}
