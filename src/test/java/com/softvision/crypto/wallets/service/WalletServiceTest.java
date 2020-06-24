package com.softvision.crypto.wallets.service;

import com.softvision.crypto.wallets.dto.DeleteResponseDTO;
import com.softvision.crypto.wallets.dto.WalletDTO;
import com.softvision.crypto.wallets.exception.ElementNotFoundException;
import com.softvision.crypto.wallets.model.entities.Wallet;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class WalletServiceTest {

    @Mock
    WalletService walletService;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllWallets() {

        HashMap priceCoinInitial = new HashMap();
        priceCoinInitial.put("USD",500.0);

        List<WalletDTO> allWalletMocked = new ArrayList<>();
        WalletDTO wallet1 = new WalletDTO();
        wallet1.setId(1L);
        wallet1.setName("wallet one");
        wallet1.setCoinPrice(priceCoinInitial);
        WalletDTO wallet2 = new WalletDTO();
        wallet2.setId(2L);
        wallet2.setName("wallet two");
        wallet2.setCoinPrice(priceCoinInitial);
        allWalletMocked.add(wallet1);
        allWalletMocked.add(wallet2);
        when(walletService.getAllWallets()).thenReturn(allWalletMocked);
        List<WalletDTO> allWalletsFromService = walletService.getAllWallets();
        Assert.assertTrue(allWalletsFromService.size() == 2);
        WalletDTO walletDTOFromService = allWalletsFromService.get(0);
        Assert.assertTrue(walletDTOFromService.getId() == wallet1.getId());
        Assert.assertTrue(walletDTOFromService.getName().equals(wallet1.getName()));
        Assert.assertTrue(walletDTOFromService.getCoinPrice().get("USD") == wallet1.getCoinPrice().get("USD"));

    }

    @Test
    void getWalletById() {

        HashMap priceCoinInitial = new HashMap();
        priceCoinInitial.put("USD",500.0);
        WalletDTO wallet1 = new WalletDTO();
        wallet1.setId(1L);
        wallet1.setName("wallet one");
        wallet1.setCoinPrice(priceCoinInitial);
        when(walletService.getWalletById(anyLong())).thenReturn(wallet1);
        WalletDTO walletDTOFromService = walletService.getWalletById(1L);
        Assert.assertTrue(walletDTOFromService.getId() == wallet1.getId());
        Assert.assertTrue(walletDTOFromService.getName().equals(wallet1.getName()));
        Assert.assertTrue(walletDTOFromService.getCoinPrice().get("USD") == wallet1.getCoinPrice().get("USD"));
    }

    @Test
    void getWalletByNotExistentId(){
        when(walletService.getWalletById(anyLong())).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class,
                ()->{
                    walletService.getWalletById(2342343L);
                });
    }

    @Test
    void saveWallet() {

        HashMap priceCoinInitial = new HashMap();
        priceCoinInitial.put("USD",500.0);

        Wallet walletToSave = new Wallet();
        walletToSave.setId(1L);
        walletToSave.setName("wallet toSave");
        walletToSave.setCoinPrice(priceCoinInitial);

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(walletToSave.getId());
        walletDTO.setName(walletToSave.getName());
        walletDTO.setCoinPrice(walletToSave.getCoinPrice());

        when(walletService.saveWallet(walletToSave)).thenReturn(walletDTO);
        WalletDTO walletDTOFromMethod = walletService.saveWallet(walletToSave);

        Assert.assertTrue(walletToSave.getId() == walletDTOFromMethod.getId());
        Assert.assertTrue(walletToSave.getName().equals(walletDTOFromMethod.getName()));
        Assert.assertTrue(walletToSave.getCoinPrice().get("USD") == walletDTOFromMethod.getCoinPrice().get("USD"));

    }

    @Test
    void deleteWallet() {

        Long idOfWalletToDelete = 1L;

        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        deleteResponseDTO.setId(idOfWalletToDelete);
        deleteResponseDTO.setResponse("Deleted");

        when(walletService.deleteWallet(anyLong())).thenReturn(deleteResponseDTO);
        DeleteResponseDTO deleteResponseDTOFromService = walletService.deleteWallet(idOfWalletToDelete);
        Assert.assertTrue(deleteResponseDTOFromService.getId() == idOfWalletToDelete);

    }

    @Test
    void deleteNotExistentWallet(){

        when(walletService.deleteWallet(anyLong())).thenThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class,
                ()->{
                    walletService.deleteWallet(6545L);
                });

    }
}