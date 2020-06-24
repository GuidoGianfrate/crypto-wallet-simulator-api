package com.softvision.crypto.wallets.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferResumeDTO {

    WalletDTO yourWallet;
    WalletDTO receiver;


}
