/**
 * 
 */
package com.dineshonjava.prodos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Dinesh.Rajput
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testSignIn() throws Exception {
		
		//TESTING: Login with correct credentials 
		this.mockMvc.perform(post("/auth/login")
			.content("{\"username\":\"dinesh\", \"password\":\"mypass\"}")
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		
		//TESTING: Login with wrong credentials 
		this.mockMvc.perform(post("/auth/login")
			.content("{\"username\":\"dinesh\", \"password\":\"mypasswrong\"}")
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().is4xxClientError());
	}
}
