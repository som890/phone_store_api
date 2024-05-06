package com.dev.phonestore.phonestore.controller;

import com.dev.phonestore.phonestore.entity.OrderInput;
import com.dev.phonestore.phonestore.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;

    @PreAuthorize("hasRole('User')")
    @PostMapping("/placeOrder")
    public void placeOrder(@RequestBody OrderInput orderInput){
        orderDetailsService.placeOrder(orderInput);
    }
}
