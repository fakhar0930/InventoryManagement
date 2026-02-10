package com.inventorymanagement.Service;

import com.inventorymanagement.Entity.Order;
import com.inventorymanagement.Entity.ProductEntity;
import com.inventorymanagement.Repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public String orderCreation(Order order){
        orderRepo.save(order);
        return "Order Saved in DataBase";
    }

}
