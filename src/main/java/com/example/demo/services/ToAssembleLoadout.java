package com.example.demo.services;

import com.example.demo.entities.Loadout;
import com.example.demo.model.LoadoutRequest;
import com.example.demo.repositories.LoadoutRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ToAssembleLoadout {

    @Autowired
    LoadoutRepository productRepository;

    public Loadout create(LoadoutRequest request) {
        request.setDate(new Date());
        return productRepository.save(new Loadout(request));
    }
}
