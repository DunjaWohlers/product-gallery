package de.neuefische.cgnjava222.productgallery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    MockMvc mockMvc;

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


}
