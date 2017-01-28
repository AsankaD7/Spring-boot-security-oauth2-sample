package com.example.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

	private TokenExtractor tokenExtractor = new BearerTokenExtractor();

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterAfter(contextClearer(), AbstractPreAuthenticatedProcessingFilter.class).authorizeRequests()
				.anyRequest().authenticated().and().httpBasic();
	}

	private OncePerRequestFilter contextClearer() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {
				if (tokenExtractor.extract(request) == null) {
					SecurityContextHolder.clearContext();
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	@Component
	public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	    private final AuthenticationManager authenticationManager;

	    @Autowired
	    public CustomWebSecurityConfigurerAdapter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	                .parentAuthenticationManager(authenticationManager);
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .formLogin()
	                    .loginPage("/login").permitAll()
	                .and()
	                    .requestMatchers().antMatchers("/login", "/oauth/token", "/oauth/confirm_access")
					.and()
	                    .authorizeRequests().anyRequest().authenticated();
	    }
	}
	
}

