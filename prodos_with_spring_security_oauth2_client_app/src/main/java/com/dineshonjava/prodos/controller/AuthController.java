package com.dineshonjava.prodos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dineshonjava.prodos.service.AuthService;

@RestController
public class AuthController {

	@Autowired
	AuthService authService;

	@GetMapping(value = "/prodos-auth")
	public String getAccessToken(@RequestParam String username, @RequestParam String password, ModelMap model) {
		String accessToken = null;
		accessToken = authService.generateAccessToken(username, password);
		return accessToken;
	}
}
