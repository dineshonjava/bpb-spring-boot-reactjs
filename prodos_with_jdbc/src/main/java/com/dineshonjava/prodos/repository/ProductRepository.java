/**
 * 
 */
package com.dineshonjava.prodos.repository;

import com.dineshonjava.prodos.domain.Product;

/**
 * @author Dinesh.Rajput
 *
 */
public interface ProductRepository {
	
	Iterable<Product> findAll();
	
	Product findOne(String id);
	
	Product save(Product product);
}
