package com.example.MVCObjectmapper;

import com.example.MVCObjectmapper.controller.ProductController;
import com.example.MVCObjectmapper.model.Product;
import com.example.MVCObjectmapper.service.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    private ProductServiceImpl service;
    @InjectMocks
    private ProductController productController;

    private final Product product1 = new Product();
    private final Product product2 = new Product();

    @BeforeEach
    void setup() {
        product1.setProductId(1L);
        product1.setName("name1");
        product1.setDescription("description1");
        product1.setPrice(10.0);
        product1.setQuantityInStock(10);
        product2.setProductId(2L);
        product2.setName("name2");
        product2.setDescription("description2");
        product2.setPrice(10.0);
        product2.setQuantityInStock(10);
    }

    @Test
    void getAllTest() throws Exception {
        when(service.getAll()).thenReturn(new ArrayList<>(List.of(product1)));

        String response = productController.getAll();

        assertTrue(response.contains("name1"));
        verify(service, times(1)).getAll();
    }

    @Test
    void getById_ReturnsProduct() {
        when(service.getById(1L)).thenReturn(product1);

        String response = productController.getById(1L);

        assertTrue(response.contains("name1"));
    }

    @Test
    void testSerialization() throws JsonProcessingException {

        String json = productController.getMapper().writeValueAsString(product1);
        String expectedJson = "{\n" +
                "  \"productId\" : 1,\n" +
                "  \"name\" : \"name1\",\n" +
                "  \"description\" : \"description1\",\n" +
                "  \"price\" : 10.0,\n" +
                "  \"quantityInStock\" : 10\n" +
                "}";

        assertEquals(expectedJson, json);
    }

    @Test
    void testDeserialization() throws JsonProcessingException {
        String expectedJson = "{\n" +
                "  \"productId\" : 1,\n" +
                "  \"name\" : \"name1\",\n" +
                "  \"description\" : \"description1\",\n" +
                "  \"price\" : 10.0,\n" +
                "  \"quantityInStock\" : 10\n" +
                "}";

        Product product = productController.getMapper().readValue(expectedJson, Product.class);

        assertEquals(1L, product.getProductId());
        assertEquals("name1", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(10, product.getQuantityInStock());
    }
}
