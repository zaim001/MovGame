package com.movigame.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movigame.entity.User;
import com.movigame.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	 public UserService(UserRepo userRepo) {
	      this.userRepo = userRepo;
	      this.passwordEncoder = new BCryptPasswordEncoder();
	    }
	
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	public Optional<User> findByUsername(String username) {
	    return Optional.ofNullable(userRepo.findUserByUsername(username));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findUserByUsername(username);
		 if (user == null) {
	            throw new UsernameNotFoundException("User not found");
	        }
	        return org.springframework.security.core.userdetails.User
	                .withUsername(user.getUsername())
	                .password(user.getPassword())
	                .authorities("USER")
	                .build();
	    }
	
}
