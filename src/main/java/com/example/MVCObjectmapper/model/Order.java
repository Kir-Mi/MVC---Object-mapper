package com.example.MVCObjectmapper.model;

import com.example.MVCObjectmapper.util.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @ManyToOne
    private Customer customer;
    @OneToMany
    private List<Product> products;
    private LocalDate orderDate;
    private String shippingAddress;
    private Double totalPrice;
    private OrderStatus orderStatus;
}
