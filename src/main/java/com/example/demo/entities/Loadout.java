package com.example.demo.entities;

import com.example.demo.model.LoadoutRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "loadouts")
@Data
@NoArgsConstructor
public class Loadout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loadoutName;
    private String primaryWeapon;
    private String secondaryWeapon;
    private BigInteger totalPrice;
    @Column(name = "date of purchase")
    private Date date;

    public Loadout(LoadoutRequest loadoutRequest){
        this.loadoutName = loadoutRequest.getLoadoutName();
        this.primaryWeapon = loadoutRequest.getPrimaryWeapon();
        this.secondaryWeapon = loadoutRequest.getSecondaryWeapon();
        this.totalPrice = loadoutRequest.getTotalPrice();
        this.date = loadoutRequest.getDate();
    }

}
