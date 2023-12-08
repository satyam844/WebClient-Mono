package com.ReactiveProgramming.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReactiveProgramming.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@RequestMapping("/home")
@RestController
public class HomeController {

	
	@Autowired
	ObjectMapper objectMapper;
	
	@GetMapping("/{userId}")
	public Mono<User> home(@PathVariable("userId") String userId) throws IOException {
		
		User user = new User("Satyam","Sonker",24,"Palo-Alto");
		Mono<String> mono1 = putUserInFile(user);
		return mono1
				.log()
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
