package com.ReactiveProgramming.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReactiveProgramming.Entity.User;
import com.ReactiveProgramming.Repository.UserRepository;
import com.ReactiveProgramming.Service.UserService;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Mono<User> insertUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Mono<User> findUserById(int id) {
		Mono<User> user = userRepository.findById(id);
		if(user != null) {
			return user;
		}
		return Mono.empty();
	}
	

}
