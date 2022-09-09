package de.neuefische.cgnjava222.productgallery;

import de.neuefische.cgnjava222.productgallery.exception.OrderNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void getMyOrders() throws Exception {
        String name = "bob";

        Product product1 = new Product("bla", "blub", "bli", List.of(new ImageInfo("http", "publID")), 5, 6);
        OrderItem orderItem = new OrderItem(product1, 5);
        SingleOrder newOrder = new SingleOrder(List.of(orderItem));
        UserOrders expectedUserOrders = new UserOrders(name, List.of(newOrder));

        orderRepo.save(expectedUserOrders);

        mock.perform(get("/api/orders/" + name).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "bob", authorities = {"USER"})
    void getMyOrdersNotExisting() throws Exception {
        String name = "bob";

        mock.perform(get("/api/orders/" + name).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(status().isNotFound()).andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotFoundException)).andExpect(result -> {
                            if (result.getResolvedException() == null) {
                                fail();
                            } else {
                                assertEquals("Order not Found from user " + name, result.getResolvedException().getMessage());
                            }
                        }
                );
    }
}
