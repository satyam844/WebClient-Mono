package com.ReactiveProgramming.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;


import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReactiveProgramming.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@RestController
public class HomeController {

	
	@Autowired
	ObjectMapper objectMapper;
	
	@GetMapping("/data")
	public Mono<ResponseEntity<Void>> func(){
		System.out.println("Inside this controller");
//		if(str != null) {
//			Mono<ResponseEntity<Void>> reponse = Mono.just(ResponseEntity.ok().build());
//			System.out.println("HHHHHHHHHHHHHHHHH");
//			return Mono.zip(processRequest(), reponse)
//					.subscribeOn(Schedulers.boundedElastic()) // Specify the scheduler if needed
//                    .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())));
			return Mono.fromCallable(()->processRequest());
//			}
//		else {
//			return Mono.just(ResponseEntity.noContent().build());
//		}
	}
	public ResponseEntity processRequest() {
		try {
			System.out.println("Thread Sleeping");
			Thread.currentThread().sleep(30000);
			System.out.println("Thread Woke Up");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().build();
		
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
		Mono<String> monoPublisher2 = Mono.just(getData());
		System.out.println(Thread.currentThread().getId() +" Hello from main");
		monoPublisher2.subscribe(data -> {
			System.out.println(data);
		});
	}
	
	public String getData() {
		try {
			System.out.println(Thread.currentThread().getId()+" Hello from getData()");
			Thread.currentThread().sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Hey";
	}
	
	@GetMapping("/{userId}")
	public Mono<User> home(@PathVariable("userId") String userId) throws IOException {
		System.out.println(Thread.currentThread().getId()+" -----------------  ");
		User user = new User("Satyam","Sonker",24,"Palo-Alto");
		Mono<String> mono1 = putUserInFile(user);
		return mono1
			//	.log()
				.map(result -> user);
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
