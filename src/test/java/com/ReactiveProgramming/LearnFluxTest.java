package com.ReactiveProgramming;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ReactiveProgramming.FluxLearnService.FluxLearnService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class LearnFluxTest {

	@Autowired
	FluxLearnService fluxService;
	
	@Test
	public void check() {
		
		fluxService.getNameFlux().subscribe(data -> {
			System.out.println(data);
			System.out.println("Done getting data");
		});
		fluxService.display();
		System.out.println("This is working");
		
		fluxService.getFruitsName().subscribe(System.out::println);
//		Flux<String> fruitNames = fluxService.getFruitsName();
//		StepVerifier.create(fruitNames)
//		.expectNextCount(4)
//		.expectComplete();
		fluxService.filterNameFlux().subscribe(System.out::println);
	}
	
}
