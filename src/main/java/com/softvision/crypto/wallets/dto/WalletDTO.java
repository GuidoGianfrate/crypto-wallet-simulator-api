package com.softvision.crypto.wallets.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class WalletDTO {
    private Long id;
    private String name;
    private HashMap<String,Double> coinPrice;

    public void addMoneyOrAddCoin(String coin, Double mount) {

        if(coinPrice.get(coin) == null){
            coinPrice.put(coin,mount);
        }else {
            coinPrice.put(coin, coinPrice.get(coin) + mount);
        }
    }
    public void sustractMoney(String coin,Double mount){
        coinPrice.put(coin, coinPrice.get(coin) - mount);
    }


}
