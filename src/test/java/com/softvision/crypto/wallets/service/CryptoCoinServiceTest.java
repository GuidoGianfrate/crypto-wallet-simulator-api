package com.softvision.crypto.wallets.service;

import com.softvision.crypto.wallets.dto.PaginationResponse;
import com.softvision.crypto.wallets.exception.BadInputException;
import com.softvision.crypto.wallets.exception.ElementNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
class CryptoCoinServiceTest {

    @Mock
    CryptoCoinService cryptoCoinService;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void gerValuesOfCoinWithHigherLimit(){
        when(cryptoCoinService.getValuesOfCoinWithPagination(anyInt(),anyInt(),anyString())).thenThrow(BadInputException.class);
        assertThrows(BadInputException.class,
                ()->{
                    cryptoCoinService.getValuesOfCoinWithPagination(0,150,"USD");
                });
    }

    @Test
    void getValuesOfCoinWithPagination() {
        int offSet = 0;
        int limit = 5;
        String valueToCompare = "BTC";

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setOffset(offSet);
        paginationResponse.setLimit(limit);

        LinkedHashMap coinPrices = new LinkedHashMap();
        coinPrices.put("USD",9000.0);
        coinPrices.put("EUR",8300.0);
        coinPrices.put("BTC",1.0);
        coinPrices.put("ETC",5.0);
        coinPrices.put("42",1.2);

        paginationResponse.setCoins(coinPrices);

        paginationResponse.setTotal(coinPrices.size());
        when(cryptoCoinService.getValuesOfCoinWithPagination(anyInt(),anyInt(),anyString())).thenReturn(paginationResponse);
        PaginationResponse paginationResponseFromService = cryptoCoinService.getValuesOfCoinWithPagination(offSet,limit,valueToCompare);
        Assert.assertTrue(paginationResponse.getTotal()==paginationResponseFromService.getTotal());
        Assert.assertTrue(paginationResponse.getLimit()==paginationResponseFromService.getLimit());
        Assert.assertTrue(paginationResponse.getOffset()==paginationResponseFromService.getOffset());
        Assert.assertTrue(paginationResponse.getCoins().get("USD")==paginationResponseFromService.getCoins().get("USD"));

    }

    @Test
    void getAllNamesOfCryptoCoins() {

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

    @Test
    void getValuesOfCoins() {

        String referenceCoin = "BTC";
        String coinToCompare = "USD,EUR,ARS";

        LinkedHashMap responseBody = new LinkedHashMap();
        responseBody.put("USD",9000.0);
        responseBody.put("EUR",8300.0);
        responseBody.put("ARS",1006543);

        when(cryptoCoinService.getValuesOfCoins(anyString(),anyString())).thenReturn(responseBody);

        LinkedHashMap responseBodyFromService = cryptoCoinService.getValuesOfCoins(referenceCoin,coinToCompare);

        Assert.assertTrue(responseBody.get("USD")==responseBodyFromService.get("USD"));
        Assert.assertTrue(responseBody.get("EUR")==responseBodyFromService.get("EUR"));
        Assert.assertTrue(responseBody.get("ARS")==responseBodyFromService.get("ARS"));

    }

    @Test
    void parseStringArrayListToStringSplitedByComas() {
        ArrayList coinNames = new ArrayList();
        coinNames.add("EUR");
        coinNames.add("BTC");
        coinNames.add("ETC");
        coinNames.add("42");
        String parsedNames = "EUR,BTC,WTC,42";
        when(cryptoCoinService.parseStringArrayListToStringSplitedByComas(coinNames)).thenReturn(parsedNames);
        String stringGettedFromService = cryptoCoinService.parseStringArrayListToStringSplitedByComas(coinNames);
        Assert.assertTrue(parsedNames.equals(stringGettedFromService));
    }
}