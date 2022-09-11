package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.OrderRepo;
import de.neuefische.cgnjava222.productgallery.exception.ProductNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.OrderDetailsItem;
import de.neuefische.cgnjava222.productgallery.model.SingleOrder;
import de.neuefische.cgnjava222.productgallery.model.SingleOrderDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                            productService.getDetailsOf(
                                    orderItem.productId()).orElseThrow(() -> new ProductNotFoundException(orderItem.productId())),
                            orderItem.count(),
                            orderItem.price())
            ).toList();
            return new SingleOrderDetails(order.id(), myName, orderDetailsItems);
        }).toList();
    }
}
