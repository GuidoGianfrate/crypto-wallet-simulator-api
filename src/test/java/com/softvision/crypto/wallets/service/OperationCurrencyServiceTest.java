package com.softvision.crypto.wallets.service;

import com.softvision.crypto.wallets.dto.TransferCurrencyDTO;
import com.softvision.crypto.wallets.dto.TransferResumeDTO;
import com.softvision.crypto.wallets.dto.WalletDTO;
import com.softvision.crypto.wallets.exception.BadInputException;
import com.softvision.crypto.wallets.exception.ElementNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class OperationCurrencyServiceTest {

    @Mock
    OperationCurrencyService operationCurrencyService;

    @Mock
    WalletService walletService;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void buyCurrency() {

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD",18000);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        WalletDTO walletDTOResponseAfterTheBuy = new WalletDTO();
        walletDTOResponseAfterTheBuy.setName("wallet one");
        walletDTOResponseAfterTheBuy.setId(1L);
        HashMap coinPriceWallet1AfterBuy = new HashMap();
        coinPriceWallet1AfterBuy.put("USD",9000);
        coinPriceWallet1AfterBuy.put("BTC",1.0);
        walletDTOResponseAfterTheBuy.setCoinPrice(coinPriceWallet1AfterBuy);

        when(operationCurrencyService.buyCurrency(anyString(),anyDouble(),anyString(),anyLong())).thenReturn(walletDTOResponseAfterTheBuy);

        WalletDTO walletDTOResponseFromService = operationCurrencyService.buyCurrency("BTC",1.0,"USD",1L);
        Assert.assertTrue(walletDTOInitial.getCoinPrice().get("BTC") == null);
        Assert.assertTrue(walletDTOResponseFromService.getCoinPrice().get("BTC") != null);
        Assert.assertTrue(walletDTOInitial.getName().equals(walletDTOResponseFromService.getName()));
        Assert.assertTrue(walletDTOInitial.getId()==walletDTOResponseFromService.getId());
        Assert.assertTrue(walletDTOInitial.getCoinPrice().get("USD") != walletDTOResponseFromService.getCoinPrice().get("USD"));
    }

    @Test
    void buyCurrencyWithoutEnoughMoney(){

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD",100);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        //1 BTC equals 9000 USD
        when(operationCurrencyService.buyCurrency(anyString(),anyDouble(),anyString(),anyLong())).thenThrow(BadInputException.class);
        assertThrows(BadInputException.class,
                ()->{
                    operationCurrencyService.buyCurrency("BTC",123.0,"USD",1L);
                });
    }

    @Test
    void buyCurrencyPayingWithCoinThatNotHave(){

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD",100);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        //the wallet not have EUR
        when(operationCurrencyService.buyCurrency(anyString(),anyDouble(),anyString(),anyLong())).thenThrow(BadInputException.class);
        assertThrows(BadInputException.class,
                ()->{
                    operationCurrencyService.buyCurrency("BTC",123.0,"EUR",1L);
                });
    }

    @Test
    void buyNotExistentCurrency(){

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD",100);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        //the wallet not have EUR
        when(operationCurrencyService.buyCurrency(anyString(),anyDouble(),anyString(),anyLong())).thenThrow(BadInputException.class);
        assertThrows(BadInputException.class,
                ()->{
                    operationCurrencyService.buyCurrency("ehfwif",123.0,"EUR",1L);
                });
    }

    @Test
    void buyWithNotExistentWallet(){

        //the wallet not have EUR
        when(operationCurrencyService.buyCurrency(anyString(),anyDouble(),anyString(),anyLong())).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class,
                ()->{
                    operationCurrencyService.buyCurrency("USD",123.0,"EUR",8L);
                });
    }

    @Test
    void transferMoneyBetweenWalletsWithSameMoney() {

        //before transaction

        HashMap coinPriceWallet1Before = new HashMap();
        coinPriceWallet1Before.put("USD",100);

        WalletDTO walletDTOInitialBefore = new WalletDTO();
        walletDTOInitialBefore.setName("wallet one");
        walletDTOInitialBefore.setId(1L);
        walletDTOInitialBefore.setCoinPrice(coinPriceWallet1Before);

        HashMap coinPriceWalletThatReceiveBefore = new HashMap();
        coinPriceWalletThatReceiveBefore.put("USD",100);

        WalletDTO walletDTOThatReceiveBefore = new WalletDTO();
        walletDTOThatReceiveBefore.setName("wallet two");
        walletDTOThatReceiveBefore.setId(2L);
        walletDTOThatReceiveBefore.setCoinPrice(coinPriceWalletThatReceiveBefore);

        //after transaction

        HashMap coinPriceWallet1After = new HashMap();
        coinPriceWallet1After.put("USD",50);

        WalletDTO walletDTOInitialAfter = new WalletDTO();
        walletDTOInitialAfter.setName("wallet one");
        walletDTOInitialAfter.setId(1L);
        walletDTOInitialAfter.setCoinPrice(coinPriceWallet1After);

        HashMap coinPriceWalletThatReceiveAfter = new HashMap();
        coinPriceWalletThatReceiveAfter.put("USD",150);

        WalletDTO walletDTOThatReceiveAfter = new WalletDTO();
        walletDTOThatReceiveAfter.setName("wallet two");
        walletDTOThatReceiveAfter.setId(2L);
        walletDTOThatReceiveAfter.setCoinPrice(coinPriceWalletThatReceiveAfter);

        TransferResumeDTO transferResumeDTO = new TransferResumeDTO();
        transferResumeDTO.setYourWallet(walletDTOInitialAfter);
        transferResumeDTO.setReceiver(walletDTOThatReceiveAfter);


        when(operationCurrencyService.transferMoneyBetweenWallets(anyLong(),anyString(),anyDouble(),
                anyString(),anyLong())).thenReturn(transferResumeDTO);


        TransferResumeDTO transferResumeDTOAfterTrasfer =   operationCurrencyService.transferMoneyBetweenWallets(walletDTOInitialBefore.getId(),
                "USD",50.0,"USD",walletDTOThatReceiveBefore.getId());

        WalletDTO walletDTOInitialFromService = transferResumeDTO.getYourWallet();
        WalletDTO walletDTOReceiverFromService = transferResumeDTO.getReceiver();


        Assert.assertTrue(walletDTOInitialFromService.getId()==walletDTOInitialBefore.getId());
        Assert.assertTrue(walletDTOReceiverFromService.getId() == walletDTOThatReceiveBefore.getId());

        Assert.assertTrue(walletDTOInitialFromService.getCoinPrice().get("USD") != walletDTOInitialBefore.getCoinPrice().get("USD"));
        Assert.assertTrue(walletDTOReceiverFromService.getCoinPrice().get("USD") != walletDTOThatReceiveBefore.getCoinPrice().get("USD"));


    }

    @Test
    void transferMoneyBetweenWalletsWithDifferent() {

        //before transaction

        HashMap coinPriceWallet1Before = new HashMap();
        coinPriceWallet1Before.put("USD",100.0);

        WalletDTO walletDTOInitialBefore = new WalletDTO();
        walletDTOInitialBefore.setName("wallet one");
        walletDTOInitialBefore.setId(1L);
        walletDTOInitialBefore.setCoinPrice(coinPriceWallet1Before);

        HashMap coinPriceWalletThatReceiveBefore = new HashMap();
        coinPriceWalletThatReceiveBefore.put("USD",100.0);

        WalletDTO walletDTOThatReceiveBefore = new WalletDTO();
        walletDTOThatReceiveBefore.setName("wallet two");
        walletDTOThatReceiveBefore.setId(2L);
        walletDTOThatReceiveBefore.setCoinPrice(coinPriceWalletThatReceiveBefore);

        //after transaction

        HashMap coinPriceWallet1After = new HashMap();
        coinPriceWallet1After.put("USD",50.0);

        WalletDTO walletDTOInitialAfter = new WalletDTO();
        walletDTOInitialAfter.setName("wallet one");
        walletDTOInitialAfter.setId(1L);
        walletDTOInitialAfter.setCoinPrice(coinPriceWallet1After);

        HashMap coinPriceWalletThatReceiveAfter = new HashMap();
        coinPriceWalletThatReceiveAfter.put("USD",100.0);
        coinPriceWalletThatReceiveAfter.put("ARS",6800.0);

        WalletDTO walletDTOThatReceiveAfter = new WalletDTO();
        walletDTOThatReceiveAfter.setName("wallet two");
        walletDTOThatReceiveAfter.setId(2L);
        walletDTOThatReceiveAfter.setCoinPrice(coinPriceWalletThatReceiveAfter);

        TransferResumeDTO transferResumeDTO = new TransferResumeDTO();
        transferResumeDTO.setYourWallet(walletDTOInitialAfter);
        transferResumeDTO.setReceiver(walletDTOThatReceiveAfter);


        when(operationCurrencyService.transferMoneyBetweenWallets(anyLong(),anyString(),anyDouble(),
                anyString(),anyLong())).thenReturn(transferResumeDTO);


        TransferResumeDTO transferResumeDTOAfterTrasfer =   operationCurrencyService.transferMoneyBetweenWallets(walletDTOInitialBefore.getId(),
                "USD",50.0,"ARS",walletDTOThatReceiveBefore.getId());

        WalletDTO walletDTOInitialFromService = transferResumeDTO.getYourWallet();
        WalletDTO walletDTOReceiverFromService = transferResumeDTO.getReceiver();


        Assert.assertTrue(walletDTOInitialFromService.getId()==walletDTOInitialBefore.getId());
        Assert.assertTrue(walletDTOReceiverFromService.getId() == walletDTOThatReceiveBefore.getId());

        Assert.assertTrue(walletDTOInitialFromService.getCoinPrice().get("USD") != walletDTOInitialBefore.getCoinPrice().get("USD"));
        System.out.println(walletDTOReceiverFromService.getCoinPrice().get("USD"));
        System.out.println(walletDTOThatReceiveBefore.getCoinPrice().get("USD"));
        Assert.assertTrue(walletDTOReceiverFromService.getCoinPrice().get("USD").equals(walletDTOThatReceiveBefore.getCoinPrice().get("USD")));
        Assert.assertTrue(walletDTOReceiverFromService.getCoinPrice().get("ARS") != null);


    }







    @Test
    void transferNotHadMoneyBetweenWallets() {

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD",100);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        HashMap coinPriceWalletThatReceive = new HashMap();
        coinPriceWalletThatReceive.put("USD",100);

        WalletDTO walletDTOThatReceive = new WalletDTO();
        walletDTOThatReceive.setName("wallet two");
        walletDTOThatReceive.setId(2L);
        walletDTOThatReceive.setCoinPrice(coinPriceWalletThatReceive);

        when(operationCurrencyService.transferMoneyBetweenWallets(anyLong(),anyString(),anyDouble(),
                                                                    anyString(),anyLong())).thenThrow(BadInputException.class);
        assertThrows(BadInputException.class,
                ()->{
                    operationCurrencyService.transferMoneyBetweenWallets(walletDTOInitial.getId(),
                            "ARS",234234.0,"USD",walletDTOThatReceive.getId());
                });

    }

    @Test
    void transferNotExistentMoneyMoneyBetweenWallets() {

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD",100);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        HashMap coinPriceWalletThatReceive = new HashMap();
        coinPriceWalletThatReceive.put("USD",100);

        WalletDTO walletDTOThatReceive = new WalletDTO();
        walletDTOThatReceive.setName("wallet two");
        walletDTOThatReceive.setId(2L);
        walletDTOThatReceive.setCoinPrice(coinPriceWalletThatReceive);

        when(operationCurrencyService.transferMoneyBetweenWallets(anyLong(),anyString(),anyDouble(),
                anyString(),anyLong())).thenThrow(BadInputException.class);
        assertThrows(BadInputException.class,
                ()->{
                    operationCurrencyService.transferMoneyBetweenWallets(walletDTOInitial.getId(),
                            "fewfwef",234234.0,"USD",walletDTOThatReceive.getId());
                });

    }

    @Test
    void transferMoneyBetweenWalletsAndReceiveNotExistentMoney() {

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD",100);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        HashMap coinPriceWalletThatReceive = new HashMap();
        coinPriceWalletThatReceive.put("USD",100);

        WalletDTO walletDTOThatReceive = new WalletDTO();
        walletDTOThatReceive.setName("wallet two");
        walletDTOThatReceive.setId(2L);
        walletDTOThatReceive.setCoinPrice(coinPriceWalletThatReceive);

        when(operationCurrencyService.transferMoneyBetweenWallets(anyLong(),anyString(),anyDouble(),
                anyString(),anyLong())).thenThrow(BadInputException.class);
        assertThrows(BadInputException.class,
                ()->{
                    operationCurrencyService.transferMoneyBetweenWallets(walletDTOInitial.getId(),
                            "USD",234234.0,"fewfe",walletDTOThatReceive.getId());
                });

    }

    @Test
    void transferMoneyBetweenWalletsAndTheDepositorNotExist() {

        HashMap coinPriceWalletThatReceive = new HashMap();
        coinPriceWalletThatReceive.put("USD",100);

        WalletDTO walletDTOThatReceive = new WalletDTO();
        walletDTOThatReceive.setName("wallet two");
        walletDTOThatReceive.setId(2L);
        walletDTOThatReceive.setCoinPrice(coinPriceWalletThatReceive);

        when(operationCurrencyService.transferMoneyBetweenWallets(anyLong(),anyString(),anyDouble(),
                anyString(),anyLong())).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class,
                ()->{
                    operationCurrencyService.transferMoneyBetweenWallets((8L),
                            "USD",234234.0,"fewfe",2L);
                });
    }

    @Test
    void transferMoneyBetweenWalletsAndTheReceiverNotExist() {

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD", 100);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        when(operationCurrencyService.transferMoneyBetweenWallets(anyLong(), anyString(), anyDouble(),
                anyString(), anyLong())).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class,
                () -> {
                    operationCurrencyService.transferMoneyBetweenWallets(1L,
                            "USD", 234234.0, "fewfe", 9L);
                });
    }
}