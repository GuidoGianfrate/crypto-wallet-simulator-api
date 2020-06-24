package com.softvision.crypto.wallets.service;

import com.softvision.crypto.wallets.dto.TransferResumeDTO;
import com.softvision.crypto.wallets.dto.WalletDTO;
import com.softvision.crypto.wallets.exception.BadInputException;
import com.softvision.crypto.wallets.exception.ConflictException;
import com.softvision.crypto.wallets.model.entities.Wallet;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

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
        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);
        walletService.saveWallet(wallet);

        return walletDTO;
    }

    public TransferResumeDTO transferMoneyBetweenWallets(Long transferFromWalletId, String coinToTransfer,
                                                         Double mountToTransfer, String coinThatWillReceive,
                                                         Long toWalletId){

        WalletDTO walletThatTransfer = walletService.getWalletById(transferFromWalletId);
        WalletDTO walletThatReceive = walletService.getWalletById(toWalletId);

        HashMap<String,Double>coinPriceOfTransferWallet = walletThatTransfer.getCoinPrice();

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
        Wallet walletToTransferEntity = modelMapper.map(walletThatTransfer, Wallet.class);
        walletService.saveWallet(walletToTransferEntity);
        Wallet walletToReceiveEntity = modelMapper.map(walletThatReceive, Wallet.class);
        walletService.saveWallet(walletToReceiveEntity);

        TransferResumeDTO transferResumeDTO = new TransferResumeDTO();
        transferResumeDTO.setYourWallet(walletThatTransfer);
        transferResumeDTO.setReceiver(walletThatReceive);

        return transferResumeDTO;
    }
















}
