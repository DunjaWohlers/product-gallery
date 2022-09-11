package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.model.ImageInfo;
import de.neuefische.cgnjava222.productgallery.model.OrderItem;
import de.neuefische.cgnjava222.productgallery.model.Product;
import de.neuefische.cgnjava222.productgallery.model.SingleOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private ProductRepo productRepo;

    @Test
    @WithMockUser(username = "bob", authorities = {"USER"})
    void getMyOrders() throws Exception {
        String userNAme = "bob";
        Product product1 = new Product("productID6", "title3", "beschreibungbla",
                List.of(new ImageInfo("url://bla", "publicCloudinaryID")),
                4, 5);
        OrderItem orderItem = new OrderItem("productID6", 7, 5);
        SingleOrder newOrder = new SingleOrder("orderID5", userNAme, List.of(orderItem));

        orderRepo.save(newOrder);
        productRepo.save(product1);

        var responseListAsString = mock.perform(
                        get("/api/orders")
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseListAsString).isEqualTo("");
    }

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
