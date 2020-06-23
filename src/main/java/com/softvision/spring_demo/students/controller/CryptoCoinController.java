package com.softvision.spring_demo.students.controller;

import com.softvision.spring_demo.students.dto.PaginationResponse;
import com.softvision.spring_demo.students.service.CryptoCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class CryptoCoinController {

    @Autowired
    CryptoCoinService cryptoCoinService;

    @GetMapping("/coins")
    public ResponseEntity<PaginationResponse> getAll(@RequestParam(value = "coin",defaultValue = "USD") String nameCoin,
                                                     @RequestParam(value = "limit") Optional<Integer> limit,
                                                     @RequestParam(value = "offSet") Optional<Integer> offSet
                                                     ){
        return new ResponseEntity<>(cryptoCoinService.getValuesOfCoinWithPagination(offSet.orElse(0),limit.orElse(30),nameCoin), HttpStatus.OK);
    }

}
