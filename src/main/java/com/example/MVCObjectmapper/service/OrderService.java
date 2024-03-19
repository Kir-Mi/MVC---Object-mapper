package com.example.MVCObjectmapper.service;

import com.example.MVCObjectmapper.model.Order;

public interface OrderService {
    Order create(Order order);
    Order getById(Long id);
}
