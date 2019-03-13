package com.dineshonjava.prodos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dineshonjava.prodos.controller.ProductController;
import com.dineshonjava.prodos.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProdosApplicationTests {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void contextLoads() {
		assertThat(productController).isNotNull();
		assertThat(productRepository).isNotNull();
		assertThat(mockMvc).isNotNull();
	}
	
	
	@Test
	public void testAuthentication() throws Exception {
		// Testing authentication with correct credentials
		this.mockMvc.perform(post("/login")
			.content("{\"username\":\"dinesh\", \"password\":\"mypass\"}")
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().is3xxRedirection());
		
		// Testing authentication with wrong credentials
		this.mockMvc.perform(post("/login")
			.content("{\"username\":\"dinesh\", \"password\":\"wrongpwd\"}")
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print()).andExpect(status().is4xxClientError());
	}

}

