package com.example.MVCObjectmapper.controller;

import com.example.MVCObjectmapper.exception.BasicException;
import com.example.MVCObjectmapper.model.Order;
import com.example.MVCObjectmapper.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public String create(@RequestBody String json) {
        Order order;
        try {
            order = getMapper().readValue(json, Order.class);
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect from json", HttpStatus.CONFLICT);
        }

        try {
            return getMapper().writeValueAsString(orderService.create(order));
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect to json", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{order_id}")
    public String getById(@PathVariable(value = "order_id") Long id) {
        try {
            return getMapper().writeValueAsString(orderService.getById(id));
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect to json", HttpStatus.CONFLICT);
        }
    }

    public ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
