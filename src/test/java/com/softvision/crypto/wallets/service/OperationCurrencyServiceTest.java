package com.softvision.crypto.wallets.service;

import com.softvision.crypto.wallets.dto.WalletDTO;
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

        WalletDTO walletDTOResponse = new WalletDTO();
        walletDTOResponse.setName("wallet one");
        walletDTOResponse.setId(1L);
        walletDTOResponse.setCoinPrice(coinPriceWallet1);

        WalletDTO walletDTOResponseAfterTheBuy = new WalletDTO();
        walletDTOResponseAfterTheBuy.setName("wallet one");
        walletDTOResponseAfterTheBuy.setId(1L);
        HashMap coinPriceWallet1AfterBuy = new HashMap();
        coinPriceWallet1.put("USD",9000);
        coinPriceWallet1.put("BTC",1.0);
        walletDTOResponseAfterTheBuy.setCoinPrice(coinPriceWallet1AfterBuy);

        when(operationCurrencyService.buyCurrency(anyString(),anyDouble(),anyString(),anyLong())).thenReturn(walletDTOResponseAfterTheBuy);

        WalletDTO walletDTOResponseFromService = operationCurrencyService.buyCurrency("BTC",1.0,"USD",1L);

        Assert.assertTrue();
        Assert.assertTrue();
        Assert.assertTrue();
        Assert.assertTrue();
        Assert.assertTrue();












    }

    @Test
    void transferMoneyBetweenWallets() {
    }
}