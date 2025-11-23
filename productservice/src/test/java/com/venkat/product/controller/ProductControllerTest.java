package com.venkat.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venkat.product.model.Product;
import com.venkat.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(10.0);
        product.setDescription("Test Description");
    }

    @Test
    void getAllProducts_returnsList() throws Exception {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getProductById_found() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getProductById_notFound() throws Exception {
        when(productService.getProductById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/products/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    @Test
    void createProduct_success() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void createProduct_validationError() throws Exception {
        Product invalid = new Product();
        invalid.setPrice(-1.0);
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct_success() throws Exception {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(product);
        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateProduct_notFound() throws Exception {
        when(productService.updateProduct(eq(2L), any(Product.class))).thenThrow(new RuntimeException("Product not found"));
        mockMvc.perform(put("/api/products/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_success() throws Exception {
        doNothing().when(productService).deleteProduct(1L);
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_notFound() throws Exception {
        doThrow(new RuntimeException("Product not found")).when(productService).deleteProduct(2L);
        mockMvc.perform(delete("/api/products/2"))
                .andExpect(status().isNotFound());
    }
}
