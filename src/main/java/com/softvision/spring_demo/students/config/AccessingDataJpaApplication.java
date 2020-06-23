package com.softvision.spring_demo.students.config;

import com.softvision.spring_demo.students.model.entities.Wallet;
import com.softvision.spring_demo.students.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@SpringBootApplication
public class AccessingDataJpaApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class);
    }

    @Bean
    public CommandLineRunner demo(WalletRepository repository) {
        return (args) -> {
            // save a few students

            HashMap coinPrice = new HashMap();
            coinPrice.put("USD",1000000.0);

            repository.save(new Wallet("Guido",coinPrice));



        };
    }





}

