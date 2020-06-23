package com.softvision.spring_demo.students.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class WalletDTO {
    private Long id;
    private String name;
    private HashMap<String,Double> coinPrice;
}
