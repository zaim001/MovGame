package com.movigame.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movigame.entity.User;

public interface UserRepo extends JpaRepository<User,Long> {
	 User findUserByEmail(String email);
}
