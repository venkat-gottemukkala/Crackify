package com.venkat.product.service;

import com.venkat.product.model.Product;
import com.venkat.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.saveProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.allProduct();
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(productRepository.getProductById(id));
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.getProductById(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        return productRepository.saveProduct(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.getProductById(id);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        // Since we're using HashMap, we need to add a delete method to the repository
        // For now, we'll handle it here by calling a repository method
        productRepository.deleteProduct(id);
    }
}
