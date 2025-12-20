package com.venkat.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venkat.product.model.Product;
import com.venkat.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping()
	public Product createProduct(@Valid @RequestBody Product product) {
		Product savedProduct = productService.saveProduct(product);
		return savedProduct;
	}

	@GetMapping("/all")
	public List<Product> findAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public Optional<Product> findById(@PathVariable Long id) {
		return productService.getProductById(id);
	}

	@PutMapping("/{id}") 
	public Optional<Object> updateProductById(@PathVariable Long id, @RequestBody Product updateProduct){
	 return  productService.updateProduct(id, updateProduct);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteProductById(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}
	

}
