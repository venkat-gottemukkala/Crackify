package com.venkat.product.service;

import com.venkat.product.model.Product;
import com.venkat.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(@Valid Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, @Valid Product product) {
        if (!productRepository.existsById(id)) {
            throw new ValidationException("Product not found");
        }
        product.setId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ValidationException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
