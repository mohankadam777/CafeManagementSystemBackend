package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(@Param("email") String email);
}
