package com.developer.jwt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.developer.jwt.entity.AuthenticationRequest;
import com.developer.jwt.entity.AuthenticationResponse;
import com.developer.jwt.security.MyUserDetailsService;
import com.developer.jwt.util.JwtUtil;

@RestController
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("jwt/test")
	public String Test() {
		
		return "wow finally got clear!!!";
	}
	
	@RequestMapping(value="/tokenn",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
	
		try {
		 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException e)
		{
		throw new Exception("please check username and password");	
		}
		
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		
		String generateToken = jwtUtil.generateToken(userDetails);
		System.out.println("token:---==" +generateToken);
		
		return ResponseEntity.ok(new AuthenticationResponse(generateToken));
		
	}
}
