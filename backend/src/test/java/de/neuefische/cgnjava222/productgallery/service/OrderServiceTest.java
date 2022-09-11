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
        OrderRepo orderRepo = mock(OrderRepo.class);
        ProductService productService = mock(ProductService.class);
        OrderService orderService = new OrderService(orderRepo, productService);

        Product product1 = new Product("productID6", "title3", "beschreibungbla", List.of(new ImageInfo("url://bla", "publicCloudinaryID")), 4, 5);

        OrderItem orderItem = new OrderItem(product1.id(), 7, 5);
        OrderDetailsItem orderDetailsItem = new OrderDetailsItem(product1, orderItem.count(), orderItem.price());

        SingleOrder newOrder = new SingleOrder("orderID5", "myName", List.of(orderItem));

        when(orderRepo.findAllByUserName("myName")).thenReturn(List.of(newOrder));
        when(productService.getDetailsOf(product1.id())).thenReturn(Optional.of(product1));

        List<SingleOrderDetails> actualOrderList = orderService.getMyOrders("myName");

        List<SingleOrderDetails> expectedOrderList = List.of(new SingleOrderDetails(newOrder.id(), "myName", List.of(orderDetailsItem)));
        System.out.println("hier");
        System.out.println(expectedOrderList);
        System.out.println(actualOrderList);
        assertThat(actualOrderList).isEqualTo(expectedOrderList);
    }
}
