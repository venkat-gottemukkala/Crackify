package com.venkat.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.venkat.product.model.Product;
import com.venkat.product.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepo;

	public ProductService(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	public Product saveProduct(Product product) {
		return productRepo.save(product);

	}

	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	public Optional<Product> getProductById(Long id) {
		return productRepo.findById(id);
	}

	public Optional<Object> updateProduct(Long id, Product updateProduct) { 
		return productRepo.findById(id).map(existingProduct ->{ 
		  existingProduct.setName(updateProduct.getName());
		  existingProduct.setPrice(updateProduct.getPrice());
		  return productRepo.save(existingProduct);
		  });
	  
	  }
	
	public boolean deleteProduct(Long id) {
		if(productRepo.existsById(id)) {
			productRepo.deleteById(id);
			return true;
		}
		return false;
	
	}

}
