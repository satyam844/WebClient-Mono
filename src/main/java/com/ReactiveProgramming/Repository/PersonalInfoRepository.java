package com.ReactiveProgramming.Repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.ReactiveProgramming.Entity.PersonalInfo;

@Repository
public interface PersonalInfoRepository extends ReactiveMongoRepository<PersonalInfo,Integer> {

}
