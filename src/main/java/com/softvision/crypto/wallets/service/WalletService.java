package com.softvision.crypto.wallets.service;


import com.softvision.crypto.wallets.dto.DeleteResponseDTO;
import com.softvision.crypto.wallets.dto.WalletDTO;
import com.softvision.crypto.wallets.exception.ElementNotFoundException;
import com.softvision.crypto.wallets.model.entities.Wallet;
import com.softvision.crypto.wallets.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public List<WalletDTO> getAllWallets() {
        List<Wallet> allWallets = walletRepository.findAll();
        //cast to dto
        List<WalletDTO> allWalletDTO
                = modelMapper.map(allWallets, new TypeToken<List<WalletDTO>>() {}.getType());
        return allWalletDTO;
    }


    @Transactional
    public WalletDTO getWalletById(Long id) {

        Optional<Wallet> walletOptional = walletRepository.findById(id);
        if(!walletOptional.isPresent())throw new ElementNotFoundException("The Wallet not exist");
        Wallet walletEntity =  walletOptional.get();
        //cast to dto
        WalletDTO walletDTO = modelMapper.map(walletEntity, WalletDTO.class);
        return walletDTO;
    }


    @Transactional
    public WalletDTO saveWallet(Wallet wallet) {
        walletRepository.save(wallet);
        //cast to dto
        WalletDTO walletDTO = modelMapper.map(wallet, WalletDTO.class);
        return walletDTO;
    }

    @Transactional
    public DeleteResponseDTO deleteWallet(Long id) {
        //if exists
        getWalletById(id);
        walletRepository.deleteById(id);
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        deleteResponseDTO.setId(id);
        deleteResponseDTO.setResponse("Deleted");
        return deleteResponseDTO;
    }
}
