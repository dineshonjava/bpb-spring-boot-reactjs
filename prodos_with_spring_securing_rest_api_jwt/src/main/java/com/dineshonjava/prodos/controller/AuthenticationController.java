/**
 * 
 */
package com.dineshonjava.prodos.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dineshonjava.prodos.dto.AccountCredentials;
import com.dineshonjava.prodos.repository.ProdosUserRepository;
import com.dineshonjava.prodos.security.JwtAuthenticationService;

/**
 * @author Dinesh.Rajput
 *
 */
@RestController
public class AuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtAuthenticationService jwtAuthenticationService;

	@Autowired
	ProdosUserRepository prodosUserRepository;

	@PostMapping("/auth/login")
	public ResponseEntity<Map<Object, Object>> signin(@RequestBody AccountCredentials credentials) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

			List<String> list = new ArrayList<>();

			list.add(this.prodosUserRepository.findByUsername(credentials.getUsername())
					.orElseThrow(
							() -> new UsernameNotFoundException("Username " + credentials.getUsername() + "not found"))
					.getRole());

			String token = jwtAuthenticationService.createToken(credentials.getUsername(), list);

			Map<Object, Object> model = new HashMap<>();
			model.put("username", credentials.getUsername());
			model.put("token", token);
			return ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
}
