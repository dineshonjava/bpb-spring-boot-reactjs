/**
 * 
 */
package com.dineshonjava.prodos.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Dinesh.Rajput
 *
 */
@Entity
@Table(name="PRODUCT")
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String type;
	
	@Column(name="desc", nullable=false, length=512)
	private String description;
	
	@Column(nullable = false)
	private String brand;
	
	public Product() {
		super();
	}

	public Product(String id, String name, String type, String description, String brand) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.brand = brand;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
