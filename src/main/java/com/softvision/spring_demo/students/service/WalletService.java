package com.softvision.spring_demo.students.service;


import com.softvision.spring_demo.students.dto.WalletDTO;
import com.softvision.spring_demo.students.exception.ElementNotFoundException;
import com.softvision.spring_demo.students.model.entities.Wallet;
import com.softvision.spring_demo.students.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;


    @Transactional
    public List<WalletDTO> getAllWallets() {

        List<Wallet> allWallets = walletRepository.findAll();
        //cast to dto
        List<WalletDTO> allWalletDTO = new ArrayList<>();
        for(Wallet temp: allWallets){
            WalletDTO walletDTO = new WalletDTO();
            walletDTO.setId(temp.getId());
            walletDTO.setName(temp.getName());
            walletDTO.setCoinPrice(temp.getCoinPrice());
            allWalletDTO.add(walletDTO);
        }
        return allWalletDTO;
    }


    @Transactional
    public WalletDTO getWalletById(Long id) {

        Optional<Wallet> walletOptional = walletRepository.findById(id);
        if(!walletOptional.isPresent())throw new ElementNotFoundException("The Wallet not exist");
        Wallet walletEntity =  walletOptional.get();
        //cast to dto
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(walletEntity.getId());
        walletDTO.setName(walletEntity.getName());
        walletDTO.setCoinPrice(walletEntity.getCoinPrice());
        return walletDTO;
    }


    @Transactional
    public WalletDTO saveWallet(Wallet wallet) {
        walletRepository.save(wallet);
        //cast to dto
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setName(wallet.getName());
        walletDTO.setCoinPrice(wallet.getCoinPrice());
        return walletDTO;
    }

    @Transactional
    public void deleteWallet(Long id) {
        //if exists
        getWalletById(id);
        walletRepository.deleteById(id);
    }
}
