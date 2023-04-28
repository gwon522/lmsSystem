package com.jingwon.lmssystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LmsSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsSystemApplication.class, args);
    }

}
