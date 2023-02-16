package com.vitu.test.service;

import java.util.List;

import com.vitu.test.dto.UserDto;
import com.vitu.test.entity.User;
import com.vitu.test.payload.PagebleResponse;

public interface UserService {

	
	UserDto createUsers(UserDto userdto);
	
	List<UserDto> getAllusers();
	
	UserDto getSingleUser(String userId);
	
	UserDto updateUsers(UserDto dto,String userId);
	
	void deleteUser(String userId);
	
	PagebleResponse<UserDto> getAllusersWIthContentsAndSorting(int pageNumber,int pageSize,String sortBy,String sortDir);

	UserDto registerUsers(UserDto dto);
}
