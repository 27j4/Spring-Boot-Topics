package com.anshulp.pubsubmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PubsubmodelApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubsubmodelApplication.class, args);
	}

}
