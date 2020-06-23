package com.softvision.spring_demo.students.model.entities;



import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "Wallets")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Wallet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    private HashMap<String,Double> coinPrice;

    public Wallet(String entryName, HashMap entryMap){
        name = entryName;
        coinPrice = entryMap;
    }
}
