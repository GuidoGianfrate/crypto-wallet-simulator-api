package com.softvision.spring_demo.students.controller;

import com.softvision.spring_demo.students.service.BuyCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class OperationController {


    @Autowired
    BuyCurrencyService buyCurrencyService;

    @PostMapping("/wallets/{id}/buy-currency")
    public ResponseEntity<?> buyCurrency(){

        Object x = buyCurrencyService.buyCurrency("BTC",5000.0,"USD",1L);

        return new ResponseEntity<>(x, HttpStatus.OK);

    }


}
