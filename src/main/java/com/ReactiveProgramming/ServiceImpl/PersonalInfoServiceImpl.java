package com.ReactiveProgramming.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReactiveProgramming.Entity.PersonalInfo;
import com.ReactiveProgramming.Repository.PersonalInfoRepository;
import com.ReactiveProgramming.Service.PersonalInfoService;

import reactor.core.publisher.Mono;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

	@Autowired
	PersonalInfoRepository personalInfoRepository;
	
	public Mono<PersonalInfo> insertPersonalInfoToMongo(PersonalInfo personalInfo){
//		Mono<PersonalInfo> persnlInf =  personalInfoRepository.save(personalInfo);
//		persnlInf.subscribe(data -> {
//			System.out.println(data.toString());
//		});
//		 System.out.println("Personal Info inserted in DB ");
//		return Mono.empty();
		 return personalInfoRepository.save(personalInfo)
		            .doOnSuccess(savedPersonalInfo -> {
		                System.out.println(savedPersonalInfo.toString());
		                System.out.println("Personal Info inserted in DB");
		            });

	}
	
	
	public Mono<PersonalInfo> findPersonlInfoById(int id){
		Mono<PersonalInfo> personalInfo = personalInfoRepository.findById(id);
		if(personalInfo != null) {
			return personalInfo;
		}
		return Mono.empty();
	}
	
}
