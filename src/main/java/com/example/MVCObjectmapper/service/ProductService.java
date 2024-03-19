package com.example.MVCObjectmapper.service;

import com.example.MVCObjectmapper.model.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long id);
    List<Product> getAll();
    Product create(Product product);
    Product update(Product product, Long id);
    void deleteById(Long id);
}
