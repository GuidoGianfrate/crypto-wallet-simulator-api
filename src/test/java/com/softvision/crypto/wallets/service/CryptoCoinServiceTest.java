package com.softvision.crypto.wallets.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CryptoCoinServiceTest {

    @Autowired
    CryptoCoinService cryptoCoinService;

    @Test
    void getAllNamesOfCryptoCoins(){
        ArrayList allNames = new ArrayList<>();
        allNames.add("USD");
        allNames.add("EUR");
        allNames.add("BTC");
        allNames.add("ETC");
        allNames.add("42");
        when(cryptoCoinService.getAllNamesOfCryptoCoins()).thenReturn(allNames);
        List allNamesFromService = cryptoCoinService.getAllNamesOfCryptoCoins();
        Assert.assertTrue(allNames.get(0).equals(allNamesFromService.get(0)));
    }











}
