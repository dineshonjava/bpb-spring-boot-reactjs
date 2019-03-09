/**
 * 
 */
package com.dineshonjava.prodos.domain;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.dineshonjava.prodos.controller.ProductController;

/**
 * @author Dinesh.Rajput
 *
 */
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, ProductResource> {

	public ProductResourceAssembler() {
		super(ProductController.class, ProductResource.class);
	}
	
	@Override
	protected ProductResource instantiateResource(Product product) {
		return new ProductResource(product);
	}
		
	@Override
	public ProductResource toResource(Product product) {
		return createResourceWithId("products/"+product.getId(), product);
	}

}
