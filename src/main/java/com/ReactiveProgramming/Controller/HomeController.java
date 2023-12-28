package com.ReactiveProgramming.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;


import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReactiveProgramming.Entity.PersonalInfo;
import com.ReactiveProgramming.Entity.User;
import com.ReactiveProgramming.Entity.UserInfo;
import com.ReactiveProgramming.ServiceImpl.PersonalInfoServiceImpl;
import com.ReactiveProgramming.ServiceImpl.UserInfoServiceImpl;
import com.ReactiveProgramming.ServiceImpl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;


@RestController
public class HomeController {

	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	PersonalInfoServiceImpl personalInfoServiceImpl;
	
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	@PostMapping("/user")
	public Mono<ResponseEntity<Void>> func(@RequestBody String str){
		System.out.println("Inside this controller");
		if(str != null) {
			Mono<ResponseEntity<Void>> response = Mono.just(ResponseEntity.status(HttpStatus.OK).build());
			Mono<Void> process = Mono.fromRunnable(() -> processRequest(str));
			System.out.println("HHHHHHHHHHHHHHHHH");
			Mono<Tuple2<ResponseEntity<Void>, Void>> combinedMono = Mono.zip(response, process);
			return combinedMono.subscribeOn(Schedulers.boundedElastic())
                    .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())));
			}
		else {
			return Mono.just(ResponseEntity.noContent().build());
		}
	}
	public void processRequest(String str) {
		System.out.println("Inside process Request");
		try {
			User user = objectMapper.readValue(str, User.class);
			Mono<User> insert = userServiceImpl.insertUser(user);
			insert.subscribe(data -> {
				System.out.println("User "+ user.toString() + "inserted to db");
			});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	@GetMapping("/test")
	public void test() {
//		Mono<String> monoPublisher = Mono.just("testing");
//		monoPublisher.subscribe(new  CoreSubscriber<String>() {
//			@Override
//			public void onNext(String data) {
//				// TODO Auto-generated method stub
//				System.out.println("Inside on next");
//	
//			}
//
//			@Override
//			public void onError(Throwable t) {
//				// TODO Auto-generated method stub
//				System.out.println("Error" + t.getMessage());
//			}
//
//			@Override
//			public void onComplete() {
//				// TODO Auto-generated method stub
//				System.out.println("Inside OnComplete");
//			}
//
//			@Override
//			public void onSubscribe(Subscription s) {
//				// TODO Auto-generated method stub
//				System.out.println("inside subscription");
//				s.request(1);;
//			}
//		});
		
		
//		Mono<String> monoPublisher2 = Mono.just(getData());
//		System.out.println(Thread.currentThread().getId() +" Hello from main");
//		monoPublisher2.subscribe(data -> {
//			System.out.println(data);
//		});
	}
	
	
	@PostMapping("/personalInfo")
	public Mono<String> personalInfo(@RequestBody String str){
		try {
			PersonalInfo info = objectMapper.readValue(str, PersonalInfo.class);
			Mono<PersonalInfo> data =	personalInfoServiceImpl.insertPersonalInfoToMongo(info);
			data.subscribe();
		 	return Mono.just("Personal Info stored in DB");
		 
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Mono.just("Data not in correct structure");
		}
		
	}
	
	@GetMapping("/{id}")
	public Mono<UserInfo> getUserInfo(@PathVariable("id") int id) throws IOException {
		 
		return userInfoServiceImpl.findUserPersonlInfo(id);
	}
	
	
	public Mono<String> putUserInFile(User user) throws IOException{
		String pathStr = "/home/satyam/Desktop/reactiveProgramming/User"+user.getFName()+".json";
		Path path = Paths.get(pathStr);
		if(!Files.exists(path)) {
			String json = "";
			try {
				Files.createFile(path);
				json = objectMapper.writeValueAsString(user);
			}
			catch(Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
			final String parsedJson = json;
			 return Mono.fromCallable(() -> {
				   System.out.println("Inside If");
		            try (FileWriter file = new FileWriter(path.toString())) {
		                file.write(parsedJson);
		                file.flush();
		                return "Success";
		            } catch (IOException e) {
		                Mono.error(e); 
		            }
		            return "fail";
		        });
		}
		else {
		   return Mono.fromCallable(() -> {
			   System.out.println("Inside else");
			   File file = new File(path.toString());
			   try {
				   User prevUser = objectMapper.readValue(file, User.class); 
				   String lastName = prevUser.getLName();
				   return "lastName";
			   }
			   catch(Exception e) {
				   Mono.error(e);
			   }
			  return "fail";
		   });
		}
	}
}
