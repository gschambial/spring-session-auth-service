package com.unsaidtalks.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
public class SpringSessionFilterInitializer extends AbstractHttpSessionApplicationInitializer {

	public SpringSessionFilterInitializer() {
		super(SpringSessionConfig.class);
	}
	
}	
