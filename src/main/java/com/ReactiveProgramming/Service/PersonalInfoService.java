package com.ReactiveProgramming.Service;

import com.ReactiveProgramming.Entity.PersonalInfo;

import reactor.core.publisher.Mono;

public interface PersonalInfoService {

	
	public Mono<PersonalInfo> insertPersonalInfoToMongo(PersonalInfo personalInfo);
	
}
