package com.dineshonjava.prodos.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OAuth2AuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -1949976839306453197L;
    
	private String token;

    public String getToken() {
		return token;
	}

	public OAuth2AuthenticationToken(String token){
        super(Arrays.asList());
        this.token = token;
    }
    
    public OAuth2AuthenticationToken(Collection<? extends GrantedAuthority> authorities, String token) {
        super(authorities);
        this.token = token;
    }

    @Override
    public Object getCredentials(){
        return this.token;
    }

    @Override
    public Object getPrincipal(){
        return this.token;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OAuth2AuthenticationToken)) {
            return false;
        } else {
            OAuth2AuthenticationToken test = (OAuth2AuthenticationToken)obj;
            if (!this.token.equals(test.token)) {
                return false;
            } else {
                return this.isAuthenticated() == test.isAuthenticated();
            }
        }
    }
    
    @Override
    public int hashCode() {
        int code = 31;
        code ^= this.token.hashCode();
        return code;
    }

}