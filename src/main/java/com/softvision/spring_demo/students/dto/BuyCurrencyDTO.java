package com.softvision.spring_demo.students.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyCurrencyDTO {

    String coinToBuy;
    String coinToPay;
    Double mountToBuy;
}
