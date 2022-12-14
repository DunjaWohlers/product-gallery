package de.neuefische.cgnjava222.productgallery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.cgnjava222.productgallery.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
class OrderIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "bob", authorities = {"USER"})
    void getMyOrders() throws Exception {
        String userNAme = "bob";
        Product product1 = new Product("productID6", "title3", "beschreibungbla",
                List.of(new ImageInfo("url://bla", "publicCloudinaryID")),
                4, 5);
        Instant now = Instant.now();
        OrderItem orderItem = new OrderItem("productID6", 7, 5);
        SingleOrder newOrder = new SingleOrder("orderID5", now, userNAme, List.of(orderItem));

        orderRepo.save(newOrder);
        productRepo.save(product1);

        var responseListAsString = mock.perform(
                        get("/api/orders")
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<SingleOrderDetails> myObjects = objectMapper.readValue(responseListAsString, new TypeReference<>() {
        });

        OrderDetailsItem orderDetailsItem = new OrderDetailsItem(product1, 7, 5);
        SingleOrderDetails expected = new SingleOrderDetails("orderID5", now, List.of(orderDetailsItem));

        assertThat(myObjects.get(0).id()).isEqualTo(expected.id());
        assertThat(myObjects.get(0).orderItems()).isEqualTo(expected.orderItems());
    }

    @Test
    @WithMockUser(username = "bob", authorities = {"USER"})
    void addOrder() throws Exception {

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
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                         {"orderItems":
                                [
                                    {
                                    "productId" : "bla",
                                    "price": 5,
                                    "count": 6
                                    }
                                ]
                                }
                        """));
    }
}
