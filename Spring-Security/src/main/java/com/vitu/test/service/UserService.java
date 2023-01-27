package com.vitu.test.service;

import java.util.List;

import com.vitu.test.dto.UserDto;
import com.vitu.test.entity.User;

public interface UserService {

	
	UserDto createUsers(UserDto userdto);
	
	List<UserDto> getAllusers();
	
	UserDto getSingleUser(String userId);
	
	UserDto updateUsers(UserDto dto,String userId);
	
	void deleteUser(String userId);
}
