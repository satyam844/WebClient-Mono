package com.ReactiveProgramming.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import com.ReactiveProgramming.Entity.PersonalInfo;
import com.ReactiveProgramming.Entity.User;
import com.ReactiveProgramming.Entity.UserInfo;
import com.ReactiveProgramming.Service.UserInfoService;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;	


@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	PersonalInfoServiceImpl personalInfoServiceImpl;
	
	@Override
	public Mono<UserInfo> findUserPersonlInfo(int id) {
		
		Mono<User> userMono = userServiceImpl.findUserById(id);
		Mono<PersonalInfo> personalInfoMono = personalInfoServiceImpl.findPersonlInfoById(id);
		Mono<Tuple2<User,PersonalInfo>> combinedMono = Mono.zip(userMono, personalInfoMono);
		return combinedMono.map(data -> {
			User user = data.getT1();
			PersonalInfo personalInfo = data.getT2();

             UserInfo userInfo = new UserInfo();
             userInfo.setId(user.getId());
             userInfo.setFName(user.getFName());
             userInfo.setLName(user.getLName()); 
             userInfo.setCollege(personalInfo.getCollege());
             userInfo.setProfession(personalInfo.getProfession());

             return userInfo;
		});
	}

	
}
