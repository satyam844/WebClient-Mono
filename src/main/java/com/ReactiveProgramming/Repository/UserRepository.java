package com.ReactiveProgramming.Repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.ReactiveProgramming.Entity.User;
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, Integer> {

}
