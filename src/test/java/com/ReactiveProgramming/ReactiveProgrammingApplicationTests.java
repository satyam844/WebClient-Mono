package com.ReactiveProgramming;

import java.time.Duration;

import javax.print.DocFlavor.STRING;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.qos.logback.core.net.SyslogOutputStream;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@SpringBootTest
class ReactiveProgrammingApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void workingWithMono() {
		Mono<String> mono = getData();
		
		Mono<String> errorMono = Mono.error(new RuntimeException("This is an error Message"));
		
//		mono.subscribe(data -> {
//			System.out.println(data +" --------------- ");
//		});
//		errorMono.then(mono).subscribe(data -> {
//			System.out.println(data +" ---------------  ");
//		});
//		mono.then(errorMono);
		  errorMono.then(mono)
          .subscribe(data -> {
              System.out.println(data);
          });
		  mono.subscribe(System.out::print);
	}
	public Mono<String> getData() {
		String str = "This is for testing";
		return Mono.just(str)
				.log();	 
	}
	
	@Test
	public void  monoWithZip() {
		Mono<String> m1 = Mono.just(getData(1)).log();
		Mono<String> m2 = Mono.just(getData(2)).log();
		
		Mono<Tuple2<String,String>> combinedMono = Mono.zip(m1, m2);
		combinedMono.subscribe(data -> {
			System.out.println(data.getT1());
			System.out.println(data.getT2());
		});
		Mono<String> m3 = Mono.just(getData(3));
		Mono<String> m4 = Mono.just(getData(4));
		Mono<Tuple2<String, String>> zipMono =  m3.zipWith(m4);
		zipMono.subscribe(System.out::print);
		
	}
	public String getData(int i) {
		if(i == 1) {
			return "Hey";
		}
		else if(i==2) {
			return "Hello";
		}
		else if(i==3) {
			return "What you doing";
		}
		else if(i == 4) {
			return "WhatsUp !";
		}
		else {
			return "What up dawg !";
		}
		
	}
	
	@Test
	public void javaStreamMap() {
		Mono<String> m1 = Mono.just("This is a Java Reactive Programming Tutorial");
		Mono<String> mapMono = m1.map(item -> item.toLowerCase());
		m1.map(item -> item.toLowerCase())
		.subscribe(data -> {
			System.out.println(data);
		});
		Mono<String[]> flatMono = m1.flatMap(item -> Mono.just(item.split(" ")));
		flatMono.subscribe(System.out::print);
	}
	
	@Test
	public void workingWithFlux() throws InterruptedException {
		Mono<String> m1 = Mono.just(getData(1));
		Mono<String> m2 = Mono.just(getData(2));
		
		System.out.println(Thread.currentThread().getName());
		
		m1.concatWith(m2)
		.delayElements(Duration.ofMillis(2000))
		.log().subscribe(data -> {
			System.out.println(Thread.currentThread().getName());
		});
		
		Thread.currentThread().sleep(3000);
		System.out.println(Thread.currentThread().getName());
		
	}

}
