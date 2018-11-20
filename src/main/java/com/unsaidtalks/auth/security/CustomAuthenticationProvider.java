package com.unsaidtalks.auth.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.unsaidtalks.auth.security.PygUserDetailService;
import com.unsaidtalks.auth.service.dto.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private final PygUserDetailService pygUserDetailService;
	
	public CustomAuthenticationProvider(PygUserDetailService pygUserDetailService){
		this.pygUserDetailService = pygUserDetailService;
	}
	
	@Override
	public Authentication authenticate(Authentication paramAuthentication) throws AuthenticationException {
	 String name = paramAuthentication.getName();
     String password = paramAuthentication.getCredentials().toString();
     System.out.println("Username: " + name);
     System.out.println("Password: " + password);
     User user = pygUserDetailService.loadUserByUsername(name);
     Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
     user.setAuthorities(authorities);
     return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override()
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}