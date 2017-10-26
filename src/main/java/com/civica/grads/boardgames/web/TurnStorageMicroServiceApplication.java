package com.civica.grads.boardgames.web;
import com.civica.grads.boardgames.model.TurnRecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class TurnStorageMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurnStorageMicroServiceApplication.class, args);
    }
    
    
    @Bean
    TurnRecord turnRecord(){
        return new TurnRecord();
    }
   
    
    
}
