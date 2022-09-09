package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.OrderRepo;
import de.neuefische.cgnjava222.productgallery.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Test
    void getMyOrderByUser() {
        OrderRepo repo = mock(OrderRepo.class);
        OrderService orderService = new OrderService(repo);

        Product product1 = new Product("bla", "blub", "bli", List.of(new ImageInfo("http", "publID")), 5, 6);
        OrderItem orderItem = new OrderItem(product1, 5);
        SingleOrder newOrder = new SingleOrder(List.of(orderItem));
        UserOrders expectedUserOrders = new UserOrders("myName", List.of(newOrder));

        when(repo.findById("myName")).thenReturn(Optional.of(expectedUserOrders));

        List<SingleOrder> actualOrderList = orderService.getMyOrder("myName");

        assertThat(actualOrderList).isEqualTo(expectedUserOrders.orderList());
    }
}
