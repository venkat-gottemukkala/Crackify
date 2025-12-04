package com.venkat.product.repository;

import java.util.*;
import org.springframework.stereotype.Repository;
import com.venkat.product.model.Product;

@Repository
public class ProductRepository {
	private final Map<Long, Product> productMap = new HashMap<>();
	private long currentId = 1L;

	public ProductRepository() {
		// Add a few sample products
		Product p1 = new Product();
		p1.setName("Laptop");
		p1.setPrice(999.99);
		p1.setDescription("High performance laptop");
		saveProduct(p1);

		Product p2 = new Product();
		p2.setName("Smartphone");
		p2.setPrice(499.99);
		p2.setDescription("Latest model smartphone");
		saveProduct(p2);

		Product p3 = new Product();
		p3.setName("Headphones");
		p3.setPrice(79.99);
		p3.setDescription("Noise cancelling headphones");
		saveProduct(p3);
	}

	public Product saveProduct(Product product) {
		if (product.getId() == null) {
			product.setId(currentId++);
		}
		productMap.put(product.getId(), product);
		return product;
	}

	public List<Product> allProduct() {
		return new ArrayList<>(productMap.values());
	}

    public Product getProductById(Long id) {
        return productMap.get(id);
    }

    public void deleteProduct(Long id) {
        productMap.remove(id);
    }
}