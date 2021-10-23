package com.example.demo.controllers;

import com.example.demo.entities.Loadout;
import com.example.demo.model.LoadoutRequest;
import com.example.demo.services.ToAssembleLoadout;

import com.example.demo.services.ToAssembleLoadout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class LoadoutController {
    @Autowired
    ToAssembleLoadout toAssembleLoadout;

    @PostMapping
    public Loadout create(@RequestBody LoadoutRequest loadout){
        return toAssembleLoadout.create(loadout);
    }
}