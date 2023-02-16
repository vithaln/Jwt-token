package com.vitu.test.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vitu.test.service.impl.CustomUserDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper helper;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");
		log.info("Header: {}",requestHeader);

		String token=null;
		String username=null;
		
		if(requestHeader!=null && requestHeader.startsWith("Bearer")) {
			//ok
			//Bearer 42dddddddddfd
			token=requestHeader.substring(7);
			//requestHeader.split(" ")[1].trim();
			
			try {
				
				username=helper.getUsernameFromToken(token);
				
				
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
}
			catch (ExpiredJwtException e) {
				e.printStackTrace();
				}
			catch (MalformedJwtException e) {
				e.printStackTrace();
				}
							
			
			
			
			
		}
		else {
			
			log.info("Inavlid Header Value..!");
		}
		
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			//fetch user from userdetalis
			
			UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
			
			//validate token
			Boolean validateToken = helper.validateToken(token, userDetails);
			//set authentication
			if(validateToken) {
				
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
	
			}
			else {
				log.info("Invalid details/token ");
			}
			
			
			
			}
		
		filterChain.doFilter(request, response);
	}


	
}
