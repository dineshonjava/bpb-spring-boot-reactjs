package com.dineshonjava.prodos.service;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public interface RestCallService {

	RestTemplate getRestTemplate();

	HttpHeaders getAuthenticationHeaderForUserData();

	String restTemplatePost(String url, Map<String, String> param, String auth);

}
