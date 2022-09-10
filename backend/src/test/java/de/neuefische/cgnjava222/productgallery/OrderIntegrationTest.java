package de.neuefische.cgnjava222.productgallery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class OrderIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private OrderRepo orderRepo;

    @Test
    @WithMockUser(username = "bob", authorities = {"USER"})
    void addOrder() throws Exception {
        String name = "bob";

        mock.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"orderItems":
                                [
                                    {
                                    "productId" : "bla",
                                    "price": 5,
                                    "count": 6
                                    }
                                ]
                                }
                                """).with(csrf())
                )
                .andExpect(status().isCreated());
    }
}
