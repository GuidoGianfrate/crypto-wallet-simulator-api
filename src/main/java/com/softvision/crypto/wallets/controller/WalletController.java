package com.softvision.crypto.wallets.controller;

import com.softvision.crypto.wallets.dto.DeleteResponseDTO;
import com.softvision.crypto.wallets.dto.WalletDTO;

import com.softvision.crypto.wallets.model.entities.Wallet;
import com.softvision.crypto.wallets.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@Controller
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/wallets")
    public ResponseEntity<List<WalletDTO>> getAllWallets(){
        return new ResponseEntity<List<WalletDTO>>(walletService.getAllWallets(), HttpStatus.OK);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> getWalletById(@PathVariable Long id){
        WalletDTO walletDTO = walletService.getWalletById(id);

        return new ResponseEntity<WalletDTO>(walletDTO,HttpStatus.OK);
    }

    @PostMapping("/wallets")
    public ResponseEntity<WalletDTO> addNewWallet(@RequestBody Wallet wallet){
        WalletDTO walletDTO = walletService.saveWallet(wallet);
        return new ResponseEntity<>(walletDTO,HttpStatus.CREATED);
    }

    @PutMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> updateWallet(@PathVariable Long id,@RequestBody Wallet wallet) {
        //if the wallet exists
        walletService.getWalletById(id);
        wallet.setId(id);
        WalletDTO walletDTO = walletService.saveWallet(wallet);
        return new ResponseEntity<WalletDTO>(walletDTO,HttpStatus.OK);
    }

    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<DeleteResponseDTO> deleteWallet(@PathVariable Long id){
        //if the wallet exists
        walletService.getWalletById(id);
        DeleteResponseDTO deleteResponseDTO = walletService.deleteWallet(id);
        return new ResponseEntity<DeleteResponseDTO>(deleteResponseDTO,HttpStatus.OK);
    }
}
