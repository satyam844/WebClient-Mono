package com.ReactiveProgramming.FluxLearnService;

import java.util.List;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class FluxLearnService {

	
	
	public void display() {
		System.out.println("This is Learn FLux Service Class");
	}
	public Flux<String> getNameFlux(){
		return Flux.just("Messi","Andreas","Xavi","Ronaldo","De Bruyne").log();
	}
	
	public Flux<String> getFruitsName(){
		List<String> list = List.of("Apple","Banana","Cherry","Dragon Fruit");
		return Flux.fromIterable(list).log();
	}
	
	public Flux<String> filterNameFlux(){
		return getNameFlux().filter(item -> item.length()>4);
	}
	
}
