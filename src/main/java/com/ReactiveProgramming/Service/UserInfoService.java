package com.ReactiveProgramming.Service;

import com.ReactiveProgramming.Entity.UserInfo;

import reactor.core.publisher.Mono;

public interface UserInfoService {

	public Mono<UserInfo> findUserPersonlInfo(int id);
}
