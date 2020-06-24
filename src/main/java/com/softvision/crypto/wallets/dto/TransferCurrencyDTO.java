package com.softvision.crypto.wallets.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferCurrencyDTO {

    String coinToTransfer;
    Double mountToTransfer;
    String coinThatWillReceive;
    Long idWallet;
}
