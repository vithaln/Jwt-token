package com.vitu.test.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vitu.test.dto.UserDto;
import com.vitu.test.entity.Role;
import com.vitu.test.entity.User;
import com.vitu.test.payload.PagebleResponse;
import com.vitu.test.repo.RoleRepo;
import com.vitu.test.repo.UserRepo;
import com.vitu.test.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo repo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PasswordEncoder encoder;
	
	private String normal_RoleId="fhfg5h4fh455";
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto createUsers(UserDto userdto) {

		userdto.setUserId(UUID.randomUUID().toString());
		
		User user = mapper.map(userdto, User.class);
		user.setPassword(encoder.encode(userdto.getPassword()));
		Role role = roleRepo.findById(normal_RoleId).orElseThrow(()-> new RuntimeException("Role not found!!"));
		
		//user.getRoles().add(role);
		
		User savedUser = repo.save(user);
		
		UserDto userDto = mapper.map(savedUser, UserDto.class);
		return userDto;
	}

	@Override
	public List<UserDto> getAllusers() {
		
		List<User> users = repo.findAll();
		List<UserDto> userdtos = users.stream().map(user-> mapper.map(user, UserDto.class )).collect(Collectors.toList());
		return userdtos;
	}

	@Override
	public UserDto getSingleUser(String userId) {
		
		User user = repo.findById(userId).orElseThrow(()-> new RuntimeException("User not found with this Id: "+userId));
		UserDto userDto = mapper.map(user, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto updateUsers(UserDto dto, String userId) {
		User user = repo.findById(userId).orElseThrow(()-> new RuntimeException("User not found with this Id: "+userId));

		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setCity(dto.getCity());
		user.setPassword(encoder.encode(dto.getPassword()));
		
		
		User upadatedUser = repo.save(user);
		UserDto  updatedDto= mapper.map(upadatedUser, UserDto.class);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) {

		User user = repo.findById(userId).orElseThrow(()-> new RuntimeException("User not found with this Id: "+userId));
repo.delete(user);
		
	}

	@Override
	public PagebleResponse<UserDto> getAllusersWIthContentsAndSorting(int pageNumber, int pageSize, String sortBy,
			String sortDir) {

		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageble=PageRequest.of(pageNumber, pageSize,sort);
		Page<User> page = repo.findAll(pageble);
		List<User> users = page.getContent();
		
		List<UserDto> userdtos = users.stream().map(u-> mapper.map(u, UserDto.class)).collect(Collectors.toList());
		PagebleResponse res=new PagebleResponse<>();
		
		res.setContents(userdtos);
		res.setPageNumber(page.getNumber());
		res.setPageSize(page.getSize());
		res.setTotalElements(page.getTotalElements());
		res.setLastPage(page.isLast());
		res.setTotalPages(page.getTotalPages());
		
		
		
		return res;
	}

	@Override
	public UserDto registerUsers(UserDto dto) {

		User user = mapper.map(dto, User.class);
		
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		Role role = roleRepo.findById(normal_RoleId).orElseThrow(()-> new RuntimeException("Role Not found"));
		user.getRoles().add(role);
		user.setPassword(encoder.encode(dto.getPassword()));

		User savedUser = repo.save(user);
		UserDto userDto = mapper.map(savedUser,UserDto.class);
		
		return userDto;
	}

}
