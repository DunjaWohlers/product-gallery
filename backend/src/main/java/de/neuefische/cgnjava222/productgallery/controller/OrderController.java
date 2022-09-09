package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.model.SingleOrder;
import de.neuefische.cgnjava222.productgallery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{userName}")
    public List<SingleOrder> getOrdersFromUser(@PathVariable String userName) {
        return orderService.getMyOrder(userName);
    }
}
