package de.neuefische.cgnjava222.productgallery.controller;

import de.neuefische.cgnjava222.productgallery.model.SingleOrderDetails;
import de.neuefische.cgnjava222.productgallery.model.NewSingleOrder;
import de.neuefische.cgnjava222.productgallery.model.SingleOrder;
import de.neuefische.cgnjava222.productgallery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public SingleOrder addOrder(@RequestBody NewSingleOrder order, Principal principal) {
        return orderService.addOrder(principal.getName(), order);
    }

    @GetMapping("")
    public List<SingleOrderDetails> getOrdersFromUser(Principal principal) {
        return orderService.getMyOrders(principal.getName());
    }
}
