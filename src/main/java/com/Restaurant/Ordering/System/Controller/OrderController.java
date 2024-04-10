package com.Restaurant.Ordering.System.Controller;

import com.Restaurant.Ordering.System.Model.OrderInput;
import com.Restaurant.Ordering.System.Model.OrderInputOneItem;
import com.Restaurant.Ordering.System.Model.OrderItems;
import com.Restaurant.Ordering.System.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/placeOrder")
    public void placeOrder(@RequestBody OrderInput orderInput){
        orderService.placeorder(orderInput);
    }

    @PostMapping("/placeOrder1")
    public void placeOrder1(@RequestBody OrderInputOneItem orderInputOneItem){
        orderService.placeorderOneItem(orderInputOneItem);
    }

}
