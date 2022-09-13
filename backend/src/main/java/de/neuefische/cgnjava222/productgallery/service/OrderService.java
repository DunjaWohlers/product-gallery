package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.OrderRepo;
import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.NewSingleOrder;
import de.neuefische.cgnjava222.productgallery.model.OrderDetailsItem;
import de.neuefische.cgnjava222.productgallery.model.SingleOrder;
import de.neuefische.cgnjava222.productgallery.model.SingleOrderDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductService productService;

    public List<SingleOrderDetails> getMyOrders(String myName) {
        List<SingleOrder> singleOrders = orderRepo.findAllByUserName(myName);
        return singleOrders.stream().map(order -> {
            List<OrderDetailsItem> orderDetailsItems = order.orderItems().stream().map(
                    orderItem -> new OrderDetailsItem(
                            productService
                                    .getDetailsOf(orderItem.productId())
                                    .orElseThrow(() -> new ProductNotFoundException(orderItem.productId())),
                            orderItem.count(),
                            orderItem.price()
                    )
            ).toList();
            return new SingleOrderDetails(order.id(), order.date(), orderDetailsItems);
        }).toList();
    }


    public SingleOrder addOrder(String name, NewSingleOrder newOrder) {
        Instant orderDate = Instant.now();
        SingleOrder order;
        if (newOrder.date() != null) {
            order = new SingleOrder(UUID.randomUUID().toString(), orderDate, name, newOrder.orderItems());
        } else {
            order = new SingleOrder(UUID.randomUUID().toString(), null, name, newOrder.orderItems());

        }
        return orderRepo.save(order);
    }
}
