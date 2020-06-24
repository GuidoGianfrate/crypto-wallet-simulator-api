package com.softvision.crypto.wallets.service;

import com.softvision.crypto.wallets.dto.TransferCurrencyDTO;
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
    void transferNotHadMoneyBetweenWallets() {

        HashMap coinPriceWallet1 = new HashMap();
        coinPriceWallet1.put("USD",100);

        WalletDTO walletDTOInitial = new WalletDTO();
        walletDTOInitial.setName("wallet one");
        walletDTOInitial.setId(1L);
        walletDTOInitial.setCoinPrice(coinPriceWallet1);

        HashMap coinPriceWalletThatReceive = new HashMap();
        coinPriceWallet1.put("USD",100);

        WalletDTO walletDTOThatReceive = new WalletDTO();
        walletDTOThatReceive.setName("wallet two");
        walletDTOThatReceive.setId(2L);
        walletDTOThatReceive.setCoinPrice(coinPriceWalletThatReceive);

        when(operationCurrencyService.transferMoneyBetweenWallets(anyLong(),anyString(),anyDouble(),
                                                                    anyString(),anyLong())).thenThrow(BadInputException.class);
        assertThrows(BadInputException.class,
                ()->{
                    operationCurrencyService.buyCurrency("ehfwif",123.0,"EUR",1L);
                });

    }
}