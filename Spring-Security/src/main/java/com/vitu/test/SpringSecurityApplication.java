package com.vitu.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vitu.test.entity.Role;
import com.vitu.test.repo.RoleRepo;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner{

	
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepo repo;
	
	private String normal_RoleId="fhfg5h4fh455";
	private String admin_RoleId="535hgf1hghh66";

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		
		try {
			Role normalRole = Role.builder().roleId(normal_RoleId).roleName("ROLE_NORMAL").build();
			Role adminRole = Role.builder().roleId(admin_RoleId).roleName("ROLE_ADMIN").build();

			repo.save(normalRole);
			repo.save(adminRole);
		}catch (Exception e) {
e.printStackTrace();		}
		
		
		
System.out.println(encoder.encode("vithal"));
		
	}

}
