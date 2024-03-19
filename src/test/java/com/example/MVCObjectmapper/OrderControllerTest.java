package com.example.MVCObjectmapper;

import com.example.MVCObjectmapper.controller.OrderController;
import com.example.MVCObjectmapper.model.Customer;
import com.example.MVCObjectmapper.model.Order;
import com.example.MVCObjectmapper.model.Product;
import com.example.MVCObjectmapper.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    OrderService service;
    @InjectMocks
    OrderController orderController;

    private final Product product1 = new Product();
    private final Customer customer = new Customer();
    private final Order order = new Order();

    @BeforeEach
    void setup() {
        product1.setProductId(1L);
        product1.setName("Product 1");
        product1.setDescription("Description of Product 1");
        product1.setPrice(10.0);
        product1.setQuantityInStock(100);
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("123456789");
        order.setOrderId(1L);
        order.setCustomer(customer);
        order.setProducts(Collections.singletonList(product1));
        order.setOrderDate(LocalDate.of(2022, 1, 1));
        order.setShippingAddress("123 Main St");
        order.setTotalPrice(10.0);
    }

    @Test
    void testOrderSerialization() throws JsonProcessingException {
        String json = orderController.getMapper().writeValueAsString(order);
        String expectedJson = "{\n" +
                "  \"orderId\" : 1,\n" +
                "  \"customer\" : {\n" +
                "    \"customerId\" : 1,\n" +
                "    \"firstName\" : \"John\",\n" +
                "    \"lastName\" : \"Doe\",\n" +
                "    \"email\" : \"john.doe@example.com\",\n" +
                "    \"contactNumber\" : \"123456789\"\n" +
                "  },\n" +
                "  \"products\" : [ {\n" +
                "    \"productId\" : 1,\n" +
                "    \"name\" : \"Product 1\",\n" +
                "    \"description\" : \"Description of Product 1\",\n" +
                "    \"price\" : 10.0,\n" +
                "    \"quantityInStock\" : 100\n" +
                "  } ],\n" +
                "  \"orderDate\" : [ 2022, 1, 1 ],\n" +
                "  \"shippingAddress\" : \"123 Main St\",\n" +
                "  \"totalPrice\" : 10.0,\n" +
                "  \"orderStatus\" : null\n" +
                "}";

        assertEquals(expectedJson, json);
    }

    @Test
    void testOrderDeserialization() throws JsonProcessingException {
        String json = "{\n" +
                "  \"orderId\" : 1,\n" +
                "  \"customer\" : {\n" +
                "    \"customerId\" : 1,\n" +
                "    \"firstName\" : \"John\",\n" +
                "    \"lastName\" : \"Doe\",\n" +
                "    \"email\" : \"john.doe@example.com\",\n" +
                "    \"contactNumber\" : \"123456789\"\n" +
                "  },\n" +
                "  \"products\" : [ {\n" +
                "    \"productId\" : 1,\n" +
                "    \"name\" : \"Product 1\",\n" +
                "    \"description\" : \"Description of Product 1\",\n" +
                "    \"price\" : 10.0,\n" +
                "    \"quantityInStock\" : 100\n" +
                "  } ],\n" +
                "  \"orderDate\" : [ 2022, 1, 1 ],\n" +
                "  \"shippingAddress\" : \"123 Main St\",\n" +
                "  \"totalPrice\" : 10.0,\n" +
                "  \"orderStatus\" : null\n" +
                "}";

        Order order = orderController.getMapper().readValue(json, Order.class);

        assertEquals(1L, order.getOrderId());
        assertEquals("John", order.getCustomer().getFirstName());
        assertEquals(1, order.getProducts().size());
        assertEquals("Product 1", order.getProducts().get(0).getName());
        assertEquals(LocalDate.of(2022, 1, 1), order.getOrderDate());
        assertEquals("123 Main St", order.getShippingAddress());
        assertEquals(10.0, order.getTotalPrice());
    }

    @Test
    public void testCreateOrder() throws JsonProcessingException {
        String jsonOrder = orderController.getMapper().writeValueAsString(order);

        when(service.create(order)).thenReturn(order);

        String responseJson = orderController.create(jsonOrder);

        assertEquals(orderController.getMapper().writeValueAsString(order), responseJson);
    }

    @Test
    public void testGetOrderById() throws JsonProcessingException {
        Long orderId = order.getOrderId();
        when(service.getById(orderId)).thenReturn(order);

        String responseJson = orderController.getById(orderId);

        assertEquals(orderController.getMapper().writeValueAsString(order), responseJson);
    }
}
