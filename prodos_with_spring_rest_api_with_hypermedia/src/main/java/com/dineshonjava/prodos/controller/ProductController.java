/**
 * 
 */
package com.dineshonjava.prodos.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
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
import com.dineshonjava.prodos.domain.ProductResource;
import com.dineshonjava.prodos.domain.ProductResourceAssembler;
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

	
	 /* @GetMapping(value = "/products", produces="application/json") 
	  public Resources<Resource<Product>> findAll(){
		  List<Product> products = new ArrayList<>(); 
		  productRepository.findAll().forEach(i -> products.add(i));
		  Resources<Resource<Product>> productResources = Resources.wrap(products);
		  productResources.add(linkTo(methodOn(ProductController.class).findAll()).withRel("products")); 
		  return productResources; 
	  }*/
	 

	@GetMapping(value = "/products", produces = "application/json")
	public Resources<ProductResource> findAll() {
		List<Product> products = new ArrayList<>();

		productRepository.findAll().forEach(i -> products.add(i));

		List<ProductResource> productResourceList = new ProductResourceAssembler().toResources(products);

		Resources<ProductResource> productResources = new Resources<ProductResource>(productResourceList);

		productResources.add(linkTo(methodOn(ProductController.class).findAll()).withRel("products"));

		return productResources;
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductResource> findProductById(@PathVariable String id) {
		if (productRepository.findById(id).isPresent()) {
			Product product = productRepository.findById(id).get();
			ProductResource productResource = new ProductResourceAssembler().toResource(product);
			return new ResponseEntity<>(productResource, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/products", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResource postProduct(@RequestBody Product product) {
		ProductResource productResource = new ProductResourceAssembler().toResource(productRepository.save(product));
		return productResource;
	}

	@PutMapping(value = "/products/{id}", consumes = "application/json", produces = "application/json")
	public ProductResource putProduct(@RequestBody Product product) {
		ProductResource productResource = new ProductResourceAssembler().toResource(productRepository.save(product));
		return productResource;
	}

	@PatchMapping(value = "/products/{id}", consumes = "application/json", produces = "application/json")
	public ProductResource patchProduct(@PathVariable String id, @RequestBody Product patch) {
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
		ProductResource productResource = new ProductResourceAssembler().toResource(productRepository.save(product));
		return productResource;
	}

	@DeleteMapping("products/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable("id") String id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
		}
	}

}
