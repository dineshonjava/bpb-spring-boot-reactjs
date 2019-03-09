/**
 * 
 */
package com.dineshonjava.prodos.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
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
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Traverson traverson;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/products")
	public String findAllProducts(ModelMap model) {
		
		ParameterizedTypeReference<Resources<Product>> productType =
				new ParameterizedTypeReference<Resources<Product>>() {};
				
		Resources<Product> productResources = traverson.follow("products").toObject(productType);
		model.put("products", productResources.getContent());
		return "products";
	}
	
	@GetMapping("/products/{id}")
	public String findProductById(ModelMap model, @PathVariable String id) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		ParameterizedTypeReference<Resource<Product>> productType =
				new ParameterizedTypeReference<Resource<Product>>() {};
				
		Resource<Product> productResource = traverson.follow("products").
				follow("$._embedded.products[0]._links.self.href").
				withTemplateParameters(parameters).toObject(productType);
		model.put("product", productResource.getContent());
		return "product";
	}
	
	@GetMapping({"/create-product", "/products/create-product"})
	public String addProduct(Product product) {
		return "add-product";
	}
	
	@PostMapping({"/create-product", "/products/create-product"})
	public String createProduct(Product product) {
		Link productLink = traverson.follow("products").asLink();
		this.restTemplate.postForEntity(productLink.expand().getHref(), product, Product.class);
		return "redirect:/products";
	}
	
	@PostMapping({"/edit/create-product"})
	public String updateProduct(ModelMap model, Product product) {
		Link productLink = traverson.follow("products").asLink();
		this.restTemplate.postForEntity(productLink.expand().getHref(), product, Product.class);
		return "redirect:/products";
	}
	
	@GetMapping("/edit/{id}")
	public String editProduct(ModelMap model, @PathVariable String id) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		ParameterizedTypeReference<Resource<Product>> productType =
				new ParameterizedTypeReference<Resource<Product>>() {};
				
		Resource<Product> productResource = traverson.follow("products").
				follow("$._embedded.products[0]._links.self.href").
				withTemplateParameters(parameters).toObject(productType);
		model.put("product", productResource.getContent());
		return "add-product";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable String id) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		ParameterizedTypeReference<Resource<Product>> productType =
				new ParameterizedTypeReference<Resource<Product>>() {};
		Resource<Product> productResource = traverson.follow("products").
						withTemplateParameters(parameters).toObject(productType);
		if (productResource != null) {
			this.restTemplate.delete(productResource.getId().getHref());
		}
		else {
			throw new IllegalStateException("The Repository with the " + id + " doesn't exist.");
		}		
		return "redirect:/products";
	}
	
}
