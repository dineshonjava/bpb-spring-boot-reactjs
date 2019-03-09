package com.dineshonjava.prodos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dineshonjava.prodos.service.ProxyService;

@RestController
public class ProxyController {

	private static final Logger logger = LoggerFactory.getLogger(ProxyController.class);

	@Autowired
	ProxyService helper;

	@GetMapping(value = "/authproxy")
	public String getAccessToken(@RequestParam String username, @RequestParam String password, ModelMap model) {
		logger.debug(" Inside getAccessToken method of ProxyController");

		String accessToken = null;

		accessToken = helper.generateAccessToken(username, password);
		logger.debug(" Exiting getAccessToken method of ProxyController with token as:{}", accessToken);

		return accessToken;
	}
}
