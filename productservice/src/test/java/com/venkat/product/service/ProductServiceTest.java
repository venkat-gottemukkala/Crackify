package com.venkat.product.service;

import com.venkat.product.model.Product;
import com.venkat.product.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(10.0);
        product.setDescription("Test Description");
    }

    @Test
    void getAllProducts_returnsList() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> found = productService.getProductById(1L);
        assertTrue(found.isPresent());
        assertEquals(product, found.get());
    }

    @Test
    void getProductById_notFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Product> found = productService.getProductById(2L);
        assertFalse(found.isPresent());
    }

    @Test
    void createProduct_success() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product created = productService.createProduct(product);
        assertEquals(product, created);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct_success() {
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product updated = productService.updateProduct(1L, product);
        assertEquals(product, updated);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct_notFound_throwsException() {
        when(productRepository.existsById(2L)).thenReturn(false);
        assertThrows(ValidationException.class, () -> productService.updateProduct(2L, product));
    }

    @Test
    void deleteProduct_success() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);
        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_notFound_throwsException() {
        when(productRepository.existsById(2L)).thenReturn(false);
        assertThrows(ValidationException.class, () -> productService.deleteProduct(2L));
    }
}
