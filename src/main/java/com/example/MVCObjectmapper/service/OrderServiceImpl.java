package com.example.MVCObjectmapper.service;

import com.example.MVCObjectmapper.exception.NotFoundException;
import com.example.MVCObjectmapper.model.Order;
import com.example.MVCObjectmapper.repository.CustomerRepository;
import com.example.MVCObjectmapper.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Order create(Order order) {
        customerRepository.save(order.getCustomer());
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found", HttpStatus.NOT_FOUND));
    }
}
