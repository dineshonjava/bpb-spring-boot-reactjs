/**
 * 
 */
package com.dineshonjava.prodos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dineshonjava.prodos.domain.Product;
import com.dineshonjava.prodos.repository.ProductRepository;

/**
 * @author Dinesh.Rajput
 *
 */

@RestController
public class ProductController {
	
	private ProductRepository productRepository;

	public ProductController(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	
	@GetMapping("/products")
	public List<Product> findAll(){
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(i -> products.add(i));
		return products;
	}
	
}
