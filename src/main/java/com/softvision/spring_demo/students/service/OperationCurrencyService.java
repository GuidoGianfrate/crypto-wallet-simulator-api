package com.softvision.spring_demo.students.service;

import com.softvision.spring_demo.students.dto.TransferResumeDTO;
import com.softvision.spring_demo.students.dto.WalletDTO;
import com.softvision.spring_demo.students.exception.BadInputException;
import com.softvision.spring_demo.students.exception.ConflictException;
import com.softvision.spring_demo.students.model.entities.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class OperationCurrencyService {

    @Autowired
    WalletService walletService;

    @Autowired
    CryptoCoinService cryptoCoinService;

    public WalletDTO buyCurrency(String coinToBuy,Double mountToBuy, String coinToPay, Long idWallet){
        //verify if the wallet has the coin to pay
        WalletDTO walletDTO = walletService.getWalletById(idWallet);
        HashMap<String,Double>coinPricesWallet = walletDTO.getCoinPrice();
        if(coinPricesWallet.get(coinToPay) == null)throw new BadInputException("The Wallet not have the coin to pay");
        //logic for know what is the value to pay
        LinkedHashMap<String,Double> coinToBuyMap = cryptoCoinService.getValuesOfCoins(coinToBuy,coinToPay);
        Double valueOfOneCoinToBuy = coinToBuyMap.get(coinToPay);
        Double valueToPayForTheWantedMount = valueOfOneCoinToBuy * mountToBuy;
        //check if the wallet has enough money
        if(coinPricesWallet.get(coinToPay) < valueToPayForTheWantedMount)throw new ConflictException("You want to pay "+ valueToPayForTheWantedMount +" "+coinToPay+", but you have only, "+ coinPricesWallet.get(coinToPay)+" "+coinToPay);

        //pay
        walletDTO.sustractMoney(coinToPay,valueToPayForTheWantedMount);
        //add Money to wallet
        walletDTO.addMoneyOrAddCoin(coinToBuy,mountToBuy);
        //save in the database
        Wallet wallet = new Wallet();
        wallet.setId(walletDTO.getId());
        wallet.setName(walletDTO.getName());
        wallet.setCoinPrice(coinPricesWallet);
        walletService.saveWallet(wallet);

        return walletDTO;
    }

    public TransferResumeDTO transferMoneyBetweenWallets(Long transferFromWalletId, String coinToTransfer,
                                                         Double mountToTransfer, String coinThatWillReceive,
                                                         Long toWalletId){

        WalletDTO walletThatTransfer = walletService.getWalletById(transferFromWalletId);
        WalletDTO walletThatReceive = walletService.getWalletById(toWalletId);

        HashMap<String,Double>coinPriceOfTransferWallet = walletThatTransfer.getCoinPrice();
        HashMap<String,Double>coinPriceOfReceiveWallet = walletThatReceive.getCoinPrice();

        //check if the transfer has the coin and the mount wanted
        if(coinPriceOfTransferWallet.get(coinToTransfer) == null)throw new ConflictException("Your wallet not have "+coinToTransfer);
        if(coinPriceOfTransferWallet.get(coinToTransfer) < mountToTransfer)throw new ConflictException("You want to transfer "+mountToTransfer+" "+coinToTransfer+" but you have only "+ coinPriceOfTransferWallet.get(coinToTransfer)+ " "+ coinToTransfer);

        String cointToDeposit;
        Double mountToDeposit;
        //change the type
        if(coinToTransfer.equals(coinThatWillReceive)){
            cointToDeposit = coinToTransfer;
            mountToDeposit = mountToTransfer;
        }else{
            LinkedHashMap<String,Double> coinToBuyMap = cryptoCoinService.getValuesOfCoins(coinToTransfer,coinThatWillReceive);
            Double valueOfOneCoinToTransferInReceiveCoin = coinToBuyMap.get(coinThatWillReceive);
            mountToDeposit = valueOfOneCoinToTransferInReceiveCoin * mountToTransfer;
            cointToDeposit = coinThatWillReceive;
        }

        //discount the money from the walletThatTransfer
        walletThatTransfer.sustractMoney(coinToTransfer,mountToTransfer);
        //add money to the receiver
        walletThatReceive.addMoneyOrAddCoin(cointToDeposit,mountToDeposit);

        //save in the database
        Wallet walletToTransferEntity = new Wallet();
        walletToTransferEntity.setId(walletThatTransfer.getId());
        walletToTransferEntity.setName(walletThatTransfer.getName());
        walletToTransferEntity.setCoinPrice(walletThatTransfer.getCoinPrice());
        walletService.saveWallet(walletToTransferEntity);

        Wallet walletToReceiveEntity = new Wallet();
        walletToReceiveEntity.setId(walletThatReceive.getId());
        walletToReceiveEntity.setName(walletThatReceive.getName());
        walletToReceiveEntity.setCoinPrice(walletThatReceive.getCoinPrice());
        walletService.saveWallet(walletToReceiveEntity);

        TransferResumeDTO transferResumeDTO = new TransferResumeDTO();
        transferResumeDTO.setYourWallet(walletThatTransfer);
        transferResumeDTO.setReceiver(walletThatReceive);

        return transferResumeDTO;




    }
















}
