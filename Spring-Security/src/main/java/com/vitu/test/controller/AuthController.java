package com.vitu.test.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitu.test.dto.JwtRequest;
import com.vitu.test.dto.JwtResponse;
import com.vitu.test.dto.UserDto;
import com.vitu.test.security.JwtHelper;
import com.vitu.test.service.impl.CustomUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtHelper helper;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private ModelMapper mapper;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest req){
		
		this.doAuthenticate(req.getEmail(),req.getPassword());
	
		UserDetails userDetails = customUserDetailService.loadUserByUsername(req.getEmail());
		System.out.println(userDetails);
		String token = helper.generateToken(userDetails);
		System.out.println(token);
		UserDto userDto = mapper.map(userDetails, UserDto.class);
		
		JwtResponse jwtResponse = JwtResponse.builder()
		.jwtToken(token).userdto(userDto).build();
		return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.CREATED);
		
		
	}


	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		
		try {
		authenticationManager.authenticate(authentication);
		}
		catch (Exception e) {
e.printStackTrace();		}
	
	
	}
	

}
