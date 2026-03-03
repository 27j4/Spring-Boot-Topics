package com.anshulp.inmemorycaching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class InmemorycachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(InmemorycachingApplication.class, args);
    }

}
