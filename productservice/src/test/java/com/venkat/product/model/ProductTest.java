package com.venkat.product.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void testGettersAndSetters() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test");
        product.setPrice(10.0);
        product.setDescription("desc");

        assertEquals(1L, product.getId());
        assertEquals("Test", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals("desc", product.getDescription());
    }
}
