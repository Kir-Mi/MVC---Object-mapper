package com.example.MVCObjectmapper.controller;

import com.example.MVCObjectmapper.exception.BasicException;
import com.example.MVCObjectmapper.model.Product;
import com.example.MVCObjectmapper.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String getAll() {
        try {
            return getMapper().writeValueAsString(productService.getAll());
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect json", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{product_id}")
    public String getById(@PathVariable(value = "product_id") Long id) {
        try {
            return getMapper().writeValueAsString(productService.getById(id));
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect json", HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public String create(@RequestBody String json) {
        Product product;
        try {
            product = getMapper().readValue(json, Product.class);
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect from json", HttpStatus.CONFLICT);
        }

        try {
            return getMapper().writeValueAsString(productService.create(product));
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect to json", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{product_id}")
    public String update(@RequestBody String json, @PathVariable(value = "product_id") Long id) {
        Product updateProduct;
        try {
            updateProduct = getMapper().readValue(json, Product.class);
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect from json", HttpStatus.CONFLICT);
        }
        try {
            return getMapper().writeValueAsString(productService.update(updateProduct, id));
        } catch (JsonProcessingException e) {
            throw new BasicException("incorrect to json", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{product_id}")
    public void deleteById(@PathVariable(value = "product_id") Long id) {
        productService.deleteById(id);
    }

    public ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
