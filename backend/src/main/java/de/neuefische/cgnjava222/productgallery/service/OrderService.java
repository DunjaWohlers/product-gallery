package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.OrderRepo;
import de.neuefische.cgnjava222.productgallery.model.NewSingleOrder;
import de.neuefische.cgnjava222.productgallery.model.SingleOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    public SingleOrder addOrder(String name, NewSingleOrder newOrder) {
        SingleOrder order = new SingleOrder(UUID.randomUUID().toString(), name, newOrder.orderItems());
        return orderRepo.save(order);
    }
}
