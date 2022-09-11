package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.model.SingleOrderDetails;
import de.neuefische.cgnjava222.productgallery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("")
    public List<SingleOrderDetails> getOrdersFromUser(Principal principal) {
        return orderService.getMyOrders(principal.getName());
    }
}
