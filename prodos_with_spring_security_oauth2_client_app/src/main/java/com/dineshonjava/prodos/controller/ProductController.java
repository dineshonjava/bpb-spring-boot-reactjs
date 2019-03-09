/**
 * 
 */
package com.dineshonjava.prodos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.dineshonjava.prodos.dto.Product;

/**
 * @author Dinesh.Rajput
 *
 */
@Controller
public class ProductController {
	
	private final String BASE_URL = "http://localhost:8181/prodos";
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/products")
	public String findAllProducts(ModelMap model) {
		List<Product>  products = restTemplate.getForObject(BASE_URL+"/products", List.class);
		model.put("products", products);
		return "products";
	}
	
	@GetMapping("/products/{id}")
	public String findProductById(ModelMap model, @PathVariable String id) {
		Product product = restTemplate.getForObject(BASE_URL+"/products/{id}", Product.class, id);
		model.put("product", product);
		return "product";
	}
	
	@GetMapping({"/create-product", "/products/create-product"})
	public String addProduct(Product product) {
		return "add-product";
	}
	
	@PostMapping({"/create-product", "/products/create-product"})
	public String createProduct(Product product) {
		restTemplate.postForObject(BASE_URL+"/products/", product, Product.class);
		return "redirect:/products";
	}
	
	@PostMapping({"/edit/create-product"})
	public String updateProduct(ModelMap model, Product product) {
		restTemplate.put(BASE_URL+"/products/", product, Product.class);
		return "redirect:/products";
	}
	
	@GetMapping("/edit/{id}")
	public String editProduct(ModelMap model, @PathVariable String id) {
		Product product = restTemplate.getForObject(BASE_URL+"/products/{id}", Product.class, id);
		model.put("product", product);
		return "add-product";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable String id) {
		restTemplate.delete(BASE_URL+"/products/{id}", id);
		return "redirect:/products";
	}
	
}
