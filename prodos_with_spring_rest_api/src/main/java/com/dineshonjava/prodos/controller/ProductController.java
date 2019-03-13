/**
 * 
 */
package com.dineshonjava.prodos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> findAll(){
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(i -> products.add(i));
		return products;
	}
	
	@GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> findProductById(@PathVariable String id){
		return productRepository.findById(id).isPresent() ? 
				new ResponseEntity<>(productRepository.findById(id).get(), HttpStatus.OK) : 
					new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value= "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Product postProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@PutMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Product putProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@PatchMapping(path="/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Product patchProduct(@PathVariable String id, @RequestBody Product patch) {
		Product product = productRepository.findById(id).get();
		if (patch.getBrand() != null) {
			product.setBrand(patch.getBrand());
		}
		if (patch.getDescription() != null) {
			product.setDescription(patch.getDescription());
		}
		if (patch.getName() != null) {
			product.setName(patch.getName());
		}
		if (patch.getType() != null) {
			product.setType(patch.getType());
		}
		return productRepository.save(product);
	}
	
	@DeleteMapping("products/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable("id") String id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {}	
	}
	
}
