/**
 * 
 */
package com.dineshonjava.prodos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dineshonjava.prodos.controller.ProductController;
import com.dineshonjava.prodos.repository.ProductRepository;

/**
 * @author Dinesh.Rajput
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductControllerTests {

	private MockMvc mockMvc;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productRepository)).build();
	}
	
	@Test
	public void testFindAllProducts() throws Exception {
		String expected = "[\r\n" + 
				"{\r\n" + 
				"id: \"MOB01\",\r\n" + 
				"name: \"Samsung A6 plus\",\r\n" + 
				"type: \"Mobile\",\r\n" + 
				"description: \"Samsung A6 plus is very nice phone with 24mp front camera\",\r\n" + 
				"brand: \"Samsung\"\r\n" + 
				"},\r\n" + 
				"{\r\n" + 
				"id: \"MOB02\",\r\n" + 
				"name: \"iPhone X plus\",\r\n" + 
				"type: \"Mobile\",\r\n" + 
				"description: \"iPhone X plus is very nice phone with 24mp front camera\",\r\n" + 
				"brand: \"Apple\"\r\n" + 
				"},\r\n" + 
				"{\r\n" + 
				"id: \"TLV01\",\r\n" + 
				"name: \"Sony Bravia KLV-50W662F 50 Inch Full HD\",\r\n" + 
				"type: \"Television\",\r\n" + 
				"description: \"Sony Bravia is full HD tv\",\r\n" + 
				"brand: \"Sony\"\r\n" + 
				"},\r\n" + 
				"{\r\n" + 
				"id: \"CAM01\",\r\n" + 
				"name: \"Canon EOS 1500D Digital SLR Camera\",\r\n" + 
				"type: \"DSLR Camera\",\r\n" + 
				"description: \"Best DSLR camera in the market\",\r\n" + 
				"brand: \"Canon\"\r\n" + 
				"},\r\n" + 
				"{\r\n" + 
				"id: \"SPK01\",\r\n" + 
				"name: \"JBL Cinema 510 5.1 with Powered Subwoofer\",\r\n" + 
				"type: \"Home Theater Speaker\",\r\n" + 
				"description: \"This sound system is suitable for the Home Theater\",\r\n" + 
				"brand: \"JBL\"\r\n" + 
				"}\r\n" + 
				"])";
		
		mockMvc.perform(get("/products"))
			.andExpect(status().isOk())
			.andExpect(content().json(expected));
	}

	@Test
	public void testFindProduct() throws Exception {
		
		String expected = "{id: \"MOB01\",name: \"Samsung A6 plus\",type: \"Mobile\",description: "
				+ "\"Samsung A6 plus is very nice phone with 24mp front camera\",brand: \"Samsung\"}";
		
		mockMvc.perform(get("/products/MOB01"))
			.andExpect(status().isOk())
			.andExpect(content().json(expected));
	}
	
	@Test
	public void testPostProduct() throws Exception {
		String newProduct = "{\"id\": \"MOB05\",\"name\": \"Samsung A6 plus\",\"type\": \"Mobile\",\"description\": "
				+ "\"Samsung A6 plus is very nice phone with 24mp front camera\",\"brand\": \"Samsung\"}";
		mockMvc.perform(post("/products")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(newProduct)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isCreated())
		.andExpect(content().json(newProduct));
	}
	
	@Test
	public void testPutProduct() throws Exception {
		String updatedProduct = "{\"id\": \"MOB03\",\"name\": \"Samsung A6 plus\",\"type\": \"Mobile\",\"description\": "
				+ "\"Samsung A6 plus is very nice phone with 24mp front camera\",\"brand\": \"Samsung\"}";
		mockMvc.perform(put("/products/MOB03")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(updatedProduct)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(content().json(updatedProduct));
	}
	
	@Test
	public void testPatchProduct() throws Exception {

		String expected = "{id: \"MOB01\",name: \"Samsung A6 plus\",type: \"Mobile\",description: "
				+ "\"Samsung A8 is very nice phone with 32mp front camera\",brand: \"Samsung\"}";
		
		String updatedProduct = "{\"id\": \"MOB01\",\"description\": "
				+ "\"Samsung A8 is very nice phone with 32mp front camera\"}";
		
		mockMvc.perform(patch("/products/MOB01")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(updatedProduct)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(content().json(expected));
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
		
		mockMvc.perform(delete("/products/MOB01"))
		.andExpect(status().isNoContent());
	}
}
