package com.example.demo.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class LoadoutRequest {
    private Long id;
    private String loadoutName;
    private String primaryWeapon;
    private String secondaryWeapon;
    private BigInteger totalPrice;
    private Date date;
}
