package com.dineshonjava.prodos.service;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.dineshonjava.prodos.dto.AccountCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserLoginServiceImpl implements UserLoginService {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AccountCredentials getAuthenticatedUserRecord(){
		if(SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2Authentication){
			OAuth2Authentication auth = (OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication(); 
			Map userPrincipalsDetails = (Map)((Map)auth.getUserAuthentication().getDetails()).get("principal");
			Map userDetails=(Map)((Map)auth.getUserAuthentication().getDetails()).get("details");
			userPrincipalsDetails.remove("enabled");
			userPrincipalsDetails.remove("credentialsNonExpired");
			userPrincipalsDetails.remove("accountNonLocked");
			userPrincipalsDetails.remove("accountNonExpired");
			userPrincipalsDetails.remove("authorities");
			if(userDetails!=null && userDetails.get("tokenValue")!=null) {
				userPrincipalsDetails.put("accessToken",userDetails.get("tokenValue"));
			}
			
			ObjectMapper oMapper = new ObjectMapper();
			return oMapper.convertValue(userPrincipalsDetails, AccountCredentials.class);
		}
		return null;
	}
}