package com.softvision.crypto.wallets.config;

import com.softvision.crypto.wallets.model.entities.Wallet;
import com.softvision.crypto.wallets.repository.WalletRepository;
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
            HashMap coinPrice = new HashMap();
            coinPrice.put("ARS",1000000.0);
            repository.save(new Wallet("Guido",coinPrice));
            repository.save(new Wallet("Marcelo",coinPrice));
        };
    }





}

