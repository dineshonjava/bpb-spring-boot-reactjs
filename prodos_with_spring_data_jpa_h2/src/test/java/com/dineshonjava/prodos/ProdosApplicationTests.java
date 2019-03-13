package com.dineshonjava.prodos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dineshonjava.prodos.controller.ProductController;
import com.dineshonjava.prodos.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdosApplicationTests {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void contextLoads() {
		assertThat(productController).isNotNull();
		assertThat(productRepository).isNotNull();
	}

}

