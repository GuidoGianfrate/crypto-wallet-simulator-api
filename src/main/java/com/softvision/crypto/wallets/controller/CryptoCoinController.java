package com.softvision.crypto.wallets.controller;

import com.softvision.crypto.wallets.dto.PaginationResponse;
import com.softvision.crypto.wallets.service.CryptoCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable
    @GetMapping("/coins")
    public ResponseEntity<PaginationResponse> getAll(@RequestParam(value = "coin",defaultValue = "USD") String nameCoin,
                                                     @RequestParam Optional<Integer> limit,
                                                     @RequestParam Optional<Integer> offSet
                                                     ){
        return new ResponseEntity<>(cryptoCoinService.getValuesOfCoinWithPagination(offSet.orElse(0),limit.orElse(30),nameCoin), HttpStatus.OK);
    }

}
