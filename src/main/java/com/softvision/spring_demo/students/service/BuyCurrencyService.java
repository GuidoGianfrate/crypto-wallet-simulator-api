package com.softvision.spring_demo.students.service;

import com.softvision.spring_demo.students.dto.WalletDTO;
import com.softvision.spring_demo.students.exception.BadInputException;
import com.softvision.spring_demo.students.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class BuyCurrencyService {

    @Autowired
    WalletService walletService;

    @Autowired
    CryptoCoinService cryptoCoinService;


    public Object buyCurrency(String coinToBuy,Double mountToBuy, String coinToPay, Long idWallet){
        //verify if the wallet has the coin to pay
        WalletDTO walletDTO = walletService.getWalletById(idWallet);
        HashMap<String,Double>coinPricesWallet = walletDTO.getCoinPrice();
        if(coinPricesWallet.get(coinToPay) == null)throw new BadInputException("The Wallet not have the coin to pay");
        //logic for know what is the value to pay
        LinkedHashMap<String,Double> coinToBuyMap = cryptoCoinService.getValuesOfCoins(coinToBuy,coinToPay);
        Double valueOfOneCoinToBuy = coinToBuyMap.get(coinToPay);
        Double valueToPayForTheWantedMount = valueOfOneCoinToBuy * mountToBuy;

        if(coinPricesWallet.get(coinToPay) < valueToPayForTheWantedMount)throw new ConflictException("You want to pay "+ valueToPayForTheWantedMount +" "+coinToPay+", but you have only, "+ coinPricesWallet.get(coinToPay)+" "+coinToPay);



        return null;

    }








}
