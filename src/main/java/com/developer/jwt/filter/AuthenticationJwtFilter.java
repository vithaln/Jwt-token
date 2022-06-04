package com.developer.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.developer.jwt.security.MyUserDetailsService;
import com.developer.jwt.util.JwtUtil;

@Component
public class AuthenticationJwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String headerRequest = request.getHeader("Authorization");
		
		String username=null;
		String token=null;
		
		
		if(headerRequest!=null && headerRequest.startsWith("Bearer"))
		{
			
			token = headerRequest.substring(7);
			username = jwtUtil.extractUsername(token);
			
			
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
		
			if(jwtUtil.validateToken(token, userDetails))
			{
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(token, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			
			
			}
		
			
		}
		
		filterChain.doFilter(request, response);
	}

}
