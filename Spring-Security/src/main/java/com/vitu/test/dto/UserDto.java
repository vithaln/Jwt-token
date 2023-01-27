package com.vitu.test.dto;

import java.util.HashSet;
import java.util.Set;

import com.vitu.test.entity.Role;
import com.vitu.test.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

	
	private String userId;
	private String password;
	private String firstName;
	private String lastName;
	private String city;
	private String email;

	private Set<Role> roles=new HashSet<>();


}
