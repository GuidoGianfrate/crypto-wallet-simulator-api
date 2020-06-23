package com.softvision.spring_demo.students.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferCurrencyDTO {

    String coinToTransfer;
    Double mountToTransfer;
    String coinToGive;
    Long idWallet;
}
