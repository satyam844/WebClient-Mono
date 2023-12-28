package com.ReactiveProgramming.Service;

import com.ReactiveProgramming.Entity.User;

import reactor.core.publisher.Mono;

public interface UserService {

	public Mono<User> insertUser(User user);
	
	public Mono<User> findUserById(int id);
}
