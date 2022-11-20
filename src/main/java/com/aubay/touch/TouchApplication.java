package com.aubay.touch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TouchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouchApplication.class, args);
    }
}
