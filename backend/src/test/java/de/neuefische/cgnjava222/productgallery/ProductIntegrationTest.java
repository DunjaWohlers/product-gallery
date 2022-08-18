package de.neuefische.cgnjava222.productgallery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    MockMvc mockMvc;
/* VERALTET - Mergeversion nötig
    @Test
    void getProducts() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/product-gallery/")
                )
                .andExpect(status().isOk());
    }*/


}
