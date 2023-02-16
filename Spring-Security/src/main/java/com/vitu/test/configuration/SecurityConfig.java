package com.vitu.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vitu.test.security.JWTAuthenticationEntryPoint;
import com.vitu.test.security.JwtFilter;
import com.vitu.test.service.impl.CustomUserDetailService;
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JWTAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtFilter filter;
	
	@Bean
	public SecurityFilterChain chain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()
		.cors().disable()
		.authorizeHttpRequests()
		.requestMatchers("/auth/users/register").permitAll()
		.requestMatchers("/auth/login").permitAll()
		.requestMatchers(HttpMethod.GET,"/users/").permitAll()
		.requestMatchers(HttpMethod.DELETE,"/users/**").hasRole("ADMIN")
		.anyRequest().authenticated().and()
		
		//.httpBasic();
		
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
		
		return httpSecurity.build();
		
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		
		return daoAuthenticationProvider;
		
		
	}
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

	return config.getAuthenticationManager();
	}
}
