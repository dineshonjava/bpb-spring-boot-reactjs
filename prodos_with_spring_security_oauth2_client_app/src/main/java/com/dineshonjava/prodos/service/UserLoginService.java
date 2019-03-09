package com.dineshonjava.prodos.service;

import com.dineshonjava.prodos.dto.AccountCredentials;

public interface UserLoginService {
	
	public AccountCredentials getAuthenticatedUserRecord();
}
