/**
 * 
 */
package com.dineshonjava.prodos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.dineshonjava.prodos.dto.Product;
import com.dineshonjava.prodos.service.AuthService;

/**
 * @author Dinesh.Rajput
 *
 */
@Controller
public class ProductController {
	
	private final String BASE_URL = "http://localhost:8181/prodos_resource_srvr";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	AuthService authService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/products")
	public String findAllProducts(ModelMap model) {
		String accessToken = authService.generateAccessToken("dinesh", "mypass");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		List<Product>  products = restTemplate.exchange(BASE_URL+"/products", HttpMethod.GET, entity, List.class).getBody();
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
