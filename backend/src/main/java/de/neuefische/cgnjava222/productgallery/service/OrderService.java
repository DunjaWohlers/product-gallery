package de.neuefische.cgnjava222.productgallery.service;

import de.neuefische.cgnjava222.productgallery.OrderRepo;
import de.neuefische.cgnjava222.productgallery.exception.OrderNotFoundException;
import de.neuefische.cgnjava222.productgallery.model.SingleOrder;
import de.neuefische.cgnjava222.productgallery.model.UserOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    public List<SingleOrder> getMyOrder(String myName) {
        UserOrders myorder = orderRepo.findById(myName).orElseThrow(() -> new OrderNotFoundException(myName));
        return myorder.orderList();
    }
}
