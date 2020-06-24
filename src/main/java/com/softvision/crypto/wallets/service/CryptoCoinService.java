package com.softvision.crypto.wallets.service;

import com.softvision.crypto.wallets.dto.PaginationResponse;
import com.softvision.crypto.wallets.exception.BadInputException;
import com.softvision.crypto.wallets.helpers.ConnectionToApi;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CryptoCoinService {

    String BASE_URL;
    String convertFromURL;
    String convertToURL;
    int maxLimit = 100;
    CryptoCoinService(){
        BASE_URL = "https://min-api.cryptocompare.com/data";
        convertFromURL = "?fsym=";
        convertToURL = "&tsyms=";
    }


    public PaginationResponse getValuesOfCoinWithPagination(int offset,int limit,String turnOverCoin ){

        if(limit>maxLimit)throw new BadInputException("The max limit is 100");
        ArrayList<String> allNames = getAllNamesOfCryptoCoins();
        int total = allNames.size();
        int subListFrom = offset;
        int subListTo = offset+limit;
        if(subListTo> total)subListTo=total;

        String wantedCoinNames = parseStringArrayListToStringSplitedByComas(allNames.subList(subListFrom,subListTo));

        LinkedHashMap keyCoinValueValue = getValuesOfCoins(turnOverCoin, wantedCoinNames);
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setTotal(total);
        paginationResponse.setCoins(keyCoinValueValue);
        paginationResponse.setOffset(offset);
        paginationResponse.setLimit(limit);

        return paginationResponse;
    }




    public ArrayList<String> getAllNamesOfCryptoCoins(){
        ConnectionToApi connectionToApi = new ConnectionToApi();
        ResponseEntity<LinkedHashMap> res =  connectionToApi.connect(BASE_URL+"/all/coinlist", HttpMethod.GET, LinkedHashMap.class,null);
        LinkedHashMap totalResponse =  res.getBody();
        LinkedHashMap objectWithArrayListOfCoins = (LinkedHashMap) totalResponse.get("Data");
        ArrayList allNamesList = new ArrayList();
        allNamesList.addAll(objectWithArrayListOfCoins.keySet());
        return allNamesList;
    }

    public LinkedHashMap getValuesOfCoins(String turnOverCoin,String listCoinToConvert){
        ConnectionToApi connectionToApi = new ConnectionToApi();
        ResponseEntity<LinkedHashMap> res =  connectionToApi.connect(BASE_URL+"/price"+convertFromURL+turnOverCoin+convertToURL+listCoinToConvert, HttpMethod.GET, LinkedHashMap.class,null);
        LinkedHashMap x = res.getBody();
        return x;
    }


    public String parseStringArrayListToStringSplitedByComas(List<String> entryCoins){
            String stringSplitedByComas ="";
            for(String tempCoinName: entryCoins){
                stringSplitedByComas = stringSplitedByComas + tempCoinName +",";
            }
            return stringSplitedByComas;
        }


}
