package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(unauthorizedHandler)
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Como se va a manejar la sesion, se mantiene mientras se consulte el API
		.and()
		.authorizeRequests()
		.antMatchers("/tokens/obtener/**")
		.permitAll()
		.anyRequest()
		.authenticated();
		
		http.authenticationProvider(this.authenticationProvider());
		
		return http.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(this.passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
