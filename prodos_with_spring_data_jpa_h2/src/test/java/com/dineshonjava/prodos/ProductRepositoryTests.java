/**
 * 
 */
package com.dineshonjava.prodos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dineshonjava.prodos.domain.Product;
import com.dineshonjava.prodos.repository.ProductRepository;

/**
 * @author Dinesh.Rajput
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void saveProduct() {
		
		productRepository.save(new Product("MOB301", "POCO", "Mobile", "Xiomi Smart Phone with 24MP front camera", "Xiomi"));
		
		Product product = productRepository.findById("MOB301").get();
		
		assertThat(product.getId()).isNotNull();
	}
	
	@Test
	public void deleteProducts() {
		productRepository.save(new Product("MOB302", "OPPO F6", "Mobile", "OPPO Smart Phone with 24MP front camera", "OPPO"));
		productRepository.save(new Product("MOB303", "VIVO V15", "Mobile", "VIVO Smart Phone with 24MP front camera", "VIVO"));
		assertThat(productRepository.findAll()).size().isEqualTo(7);
		productRepository.deleteAll();
		assertThat(productRepository.findAll()).isEmpty();
	}
}
