package com.softvision.crypto.wallets.controller;

import com.softvision.crypto.wallets.dto.BuyCurrencyDTO;
import com.softvision.crypto.wallets.dto.TransferCurrencyDTO;
import com.softvision.crypto.wallets.dto.TransferResumeDTO;
import com.softvision.crypto.wallets.dto.WalletDTO;
import com.softvision.crypto.wallets.service.OperationCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class OperationController {


    @Autowired
    OperationCurrencyService operationCurrencyService;

    @PostMapping("/wallets/{idWallet}/currency/buy")
    public ResponseEntity<WalletDTO> buyCurrency(@PathVariable Long idWallet, @RequestBody BuyCurrencyDTO buyCurrencyDTO){

        WalletDTO walletDTO = operationCurrencyService.buyCurrency(buyCurrencyDTO.getCoinToBuy(),
                                    buyCurrencyDTO.getMountToBuy(),buyCurrencyDTO.getCoinToPay(),idWallet);
        return new ResponseEntity<>(walletDTO, HttpStatus.OK);
    }

    @PostMapping("/wallets/{idWallet}/currency/transfer")
    public ResponseEntity<TransferResumeDTO> transferCurrency(@PathVariable Long idWallet, @RequestBody TransferCurrencyDTO transferCurrencyDTO){

        TransferResumeDTO transferResumeDTO = operationCurrencyService.transferMoneyBetweenWallets(idWallet,
                                                            transferCurrencyDTO.getCoinToTransfer(),
                                                            transferCurrencyDTO.getMountToTransfer(),
                                                            transferCurrencyDTO.getCoinThatWillReceive(),
                                                            transferCurrencyDTO.getIdWallet());


        return new ResponseEntity<>(transferResumeDTO,HttpStatus.OK);
    }


}
