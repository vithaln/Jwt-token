package com.vitu.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitu.test.dto.UserDto;
import com.vitu.test.service.UserService;

@RestController
@RequestMapping("/users")
public class UserControler {
	
	@Autowired
	private UserService service;
	
	
	@PostMapping()
	public ResponseEntity<UserDto> savUsers(@RequestBody UserDto dto){
		
		UserDto createUsers = service.createUsers(dto);
		
		return new ResponseEntity<UserDto>(createUsers,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto,@PathVariable String userId){
		
		UserDto updatedUser = service.updateUsers(dto, userId);
		
		return new ResponseEntity<UserDto>(updatedUser,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId){
		
		UserDto singleUser = service.getSingleUser(userId);
		
		return new ResponseEntity<UserDto>(singleUser,HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		List<UserDto> allusers = service.getAllusers();
		
		return new ResponseEntity<List<UserDto>>(allusers,HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteUser(@PathVariable String userId) {
		service.deleteUser(userId);
		return "User has been delted successfully...";
		
	}
}
