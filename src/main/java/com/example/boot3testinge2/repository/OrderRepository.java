package com.example.boot3testinge2.repository;

import com.example.boot3testinge2.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

public class OrderRepository {

    public List<Order> getOrders(String productCode) {
        //query DB or fetch from REST API
        return List.of();
    }

    public List<Order> getAllOrders() {
        //query DB or fetch from REST API
        return List.of();
    }
}