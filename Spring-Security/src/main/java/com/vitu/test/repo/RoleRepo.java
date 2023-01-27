package com.vitu.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitu.test.entity.Role;

public interface RoleRepo extends JpaRepository<Role,String>{

}
