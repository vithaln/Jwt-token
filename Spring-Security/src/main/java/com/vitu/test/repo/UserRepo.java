package com.vitu.test.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitu.test.entity.User;

public interface UserRepo extends JpaRepository<User, String> {

	Optional<User> findByEmail(String email);
}
