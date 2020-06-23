package com.softvision.spring_demo.students.controller;

import com.softvision.spring_demo.students.dto.DeleteResponseDTO;
import com.softvision.spring_demo.students.dto.WalletDTO;

import com.softvision.spring_demo.students.model.entities.Wallet;
import com.softvision.spring_demo.students.service.WalletService;
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
        WalletDTO walletDTO = walletService.saveStudent(wallet);
        return new ResponseEntity<>(walletDTO,HttpStatus.CREATED);
    }

    @PutMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> updateWallet(@PathVariable Long id,@RequestBody Wallet wallet) {
        //if the student exists
        walletService.getWalletById(id);
        wallet.setId(id);
        WalletDTO walletDTO = walletService.saveStudent(wallet);
        return new ResponseEntity<WalletDTO>(walletDTO,HttpStatus.OK);
    }

    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<DeleteResponseDTO> deleteWallet(@PathVariable Long id){
        //if the student exists
        walletService.getWalletById(id);
        walletService.deleteWallet(id);
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        deleteResponseDTO.setId(id);
        deleteResponseDTO.setResponse("Deleted");
        return new ResponseEntity<DeleteResponseDTO>(deleteResponseDTO,HttpStatus.OK);
    }
}
