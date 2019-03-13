package com.dineshonjava.prodos.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dineshonjava.prodos.dto.AuthResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private RestCallService restCallService;
	
	@Value("${security.oauth2.client.access-token-uri}")
	private String ACCESS_TOKEN_API_DOMAIN_URI;
	
	@Value("${security.oauth2.client.client-id}")
	private String ACCESS_TOKEN_API_CLIENT_ID;
	
	@Value("${security.oauth2.client.client-secret}")
	private String ACCESS_TOKEN_API_CLIENT_SECRET;

	public String generateAccessToken(String username, String password) {
		String response = null;
		Map<String, String> params = new HashMap<String, String>();

		params.put("username", username);
		params.put("password", password);
		params.put("grant_type", "password");

		String auth = ACCESS_TOKEN_API_CLIENT_ID+":"+ACCESS_TOKEN_API_CLIENT_SECRET;
		
		try {
			response = restCallService.restTemplatePost(ACCESS_TOKEN_API_DOMAIN_URI, params, auth);
		} catch (Exception e) {
			logger.error("Exception while calling access token API:{} ", e.getMessage());
		}

		if (!StringUtils.isEmpty(response)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				AuthResponseDto authResponseDto = mapper.readValue(response, AuthResponseDto.class);

				response = authResponseDto.getAccess_token();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}
}