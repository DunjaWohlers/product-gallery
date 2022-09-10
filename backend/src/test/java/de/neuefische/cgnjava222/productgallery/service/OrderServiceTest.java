package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.OrderRepo;
import de.neuefische.cgnjava222.productgallery.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Test
    void addOrder() {
        OrderRepo repo = mock(OrderRepo.class);
        OrderService orderService = new OrderService(repo);

        String id = "id4";
        String name = "name";

        Product product1 = new Product("bla", "blub", "bli", List.of(new ImageInfo("http", "publID")), 5, 6);
        OrderItem orderItem = new OrderItem(product1.id(), 5, 3);

        NewSingleOrder newOrder = new NewSingleOrder(List.of(orderItem));
        SingleOrder expectedOrder = new SingleOrder(id, name, List.of(orderItem));

        when(repo.save(any(SingleOrder.class))).thenReturn(expectedOrder);

        SingleOrder addedActualOrder = orderService.addOrder(name, newOrder);
        assertThat(addedActualOrder.orderItems()).isEqualTo(expectedOrder.orderItems());
    }
}
