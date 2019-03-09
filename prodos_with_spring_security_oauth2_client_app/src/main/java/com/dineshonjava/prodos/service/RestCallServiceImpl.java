package com.dineshonjava.prodos.service;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class RestCallServiceImpl implements RestCallService{
	
	private static Logger LOGGER = LoggerFactory.getLogger(RestCallServiceImpl.class);
	
	@Autowired
	UserLoginService loginService;
	private int timeout = 30 * 1000;
	
	public RestTemplate getRestTemplate(){
	    RequestConfig config = RequestConfig.custom()
	                   .setConnectTimeout(timeout)
	                   .setConnectionRequestTimeout(timeout)	
	                   .setSocketTimeout(timeout).setAuthenticationEnabled(true)
	                   .build();
	    CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	    RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
	    return restTemplate;
	}
	
	public HttpHeaders getAuthenticationHeaderForUserData(){
		OAuth2Authentication auth = (OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication(); 
		if(auth != null){
			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
			if(details != null && !StringUtils.isEmpty(details.getTokenValue())){
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Authorization", "Bearer " + details.getTokenValue());
				return headers;
			}
		}
		return null;
	}
	
	@Override
	public String restTemplatePost(String url, Map<String, String> param, String auth) {
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();

		if (!ObjectUtils.isEmpty(auth)) {
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(encodedAuth);
			headers.set("Authorization", authHeader);//Basic ZGluZXNob25qYXZhOmRpbmVzaG9uamF2YQ==
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		} else {
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		}

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		for (Entry<String, String> entry : param.entrySet()) {
			params.add(entry.getKey(), entry.getValue());
		}

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		ResponseEntity<String> response = null;

		LOGGER.debug(" POST API " + url);
		LOGGER.debug(" POST API REQUEST " + request);

		if (!ObjectUtils.isEmpty(auth)) {
			response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		} else {
			response = restTemplate.postForEntity(url, request, String.class);
		}
		LOGGER.debug(" Response for POST API:{} is {} ", url, response.getBody());

		return response.getBody();
	}
	
}