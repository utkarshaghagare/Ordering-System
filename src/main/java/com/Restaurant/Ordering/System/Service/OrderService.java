package com.Restaurant.Ordering.System.Service;

import com.Restaurant.Ordering.System.Entity.OrderHistory;
import com.Restaurant.Ordering.System.Model.OrderInput;
import com.Restaurant.Ordering.System.Model.OrderInputOneItem;
import com.Restaurant.Ordering.System.Model.OrderItems;
import com.Restaurant.Ordering.System.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public void placeorder(OrderInput orderInput){
        List<OrderItems> orderitems = orderInput.getItems();
        for(OrderItems orderItem : orderitems){
            OrderHistory orderHistory= new OrderHistory();
            orderHistory.setCustomerName(orderInput.getCustomerName());
            orderHistory.setTableNumber(orderInput.getTableNumber());
            orderHistory.setItemName(orderItem.getItemName());
            orderHistory.setQuantity(orderItem.getQuantity());
            orderHistory.setDescription(orderItem.getDescription());
            orderHistory.setPrice(orderItem.getQuantity()*+
                     orderHistory.getPrice());
            orderRepository.save(orderHistory);
        }
        System.out.println("order Placed");
    }

    public void placeorderOneItem(OrderInputOneItem orderInputOneItem) {

        OrderHistory orderHistory= new OrderHistory();
        orderHistory.setCustomerName(orderInputOneItem.getCustomerName());
        orderHistory.setTableNumber(orderInputOneItem.getTableNumber());
        orderHistory.setItemName(orderInputOneItem.getOrderItem().getItemName());
        orderHistory.setQuantity(orderInputOneItem.getOrderItem().getQuantity());
        orderHistory.setDescription(orderInputOneItem.getOrderItem().getDescription());
        orderHistory.setPrice(orderInputOneItem.getOrderItem().getPrice());
        orderHistory.setTotalAmount(orderInputOneItem.getOrderItem().getQuantity()*
                orderInputOneItem.getOrderItem().getPrice());
        orderRepository.save(orderHistory);

    }
}
