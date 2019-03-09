/**
 * 
 */
package com.dineshonjava.prodos.domain;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * @author Dinesh.Rajput
 *
 */
@Relation(value="product", collectionRelation="products")
public class ProductResource extends ResourceSupport{
	
	private String name;
	
	private String type;
	
	private String description;
	
	private String brand;
	
	public ProductResource(Product product) {
		this.name = product.getName();
		this.type = product.getType();
		this.description = product.getDescription();
		this.brand = product.getBrand();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
