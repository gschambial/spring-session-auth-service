package com.unsaidtalks.auth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.unsaidtalks.auth.security.CustomAuthenticationProvider;
import com.unsaidtalks.auth.security.CustomLoginFailureHandler;
import com.unsaidtalks.auth.security.MySavedRequestAwareAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MicroserviceSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
	@Autowired
	private MySavedRequestAwareAuthenticationSuccessHandler successHandler;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http
		.csrf().disable()
		.anonymous()
	.and()
		.cors()
	.and()
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/", true)
		.loginProcessingUrl("/login/authenticate")
		.failureHandler(new CustomLoginFailureHandler())
		.successHandler(successHandler)
		.defaultSuccessUrl("/success")
	.and()
		.logout()
		.deleteCookies("SESSION")
		.logoutUrl("/logout")
		.logoutSuccessUrl("/logoutsuccess")
	.and()
		.authorizeRequests()
		.antMatchers(
				"/auth/**",
				"/signin/**",
				"/login",
				"/error",
				"/signup",
				"/css/**",
				"/js/**"
		).permitAll()
		.antMatchers("/**")
			.authenticated();
    		//.permitAll();
    }

    @Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8100", "*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token", "x-requested-with","Set-Cookie"));
        configuration.setExposedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token", "x-requested-with","Set-Cookie"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}
