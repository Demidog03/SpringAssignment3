package com.example.demo.controllers;

import com.example.demo.entities.Loadout;
import com.example.demo.entities.User;
import com.example.demo.model.LoadoutRequest;
import com.example.demo.services.ToAssembleLoadout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class LoadoutController {
    @Autowired
    ToAssembleLoadout toAssembleLoadout;

    @PostMapping("/create")
    public Loadout create(@RequestBody LoadoutRequest loadout){
        return toAssembleLoadout.createLoadout(loadout);
    }
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam int id) {
        boolean result = toAssembleLoadout.deleteLoadout(id);
        if(result){
            return ResponseEntity.ok().body("Loadout deleted");
        }
        return ResponseEntity.badRequest().body("ID of loadout does not exist");
    }
    @PostMapping("/update")
    public void update(@RequestBody Loadout loadout){
        toAssembleLoadout.updateLoadout(loadout);
    }
    @GetMapping("/get")
    public ResponseEntity get(@RequestParam int id){
        Loadout loadoutRequest = toAssembleLoadout.getLoadout(id);
        if (loadoutRequest == null) {
            return new ResponseEntity("Loadout not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(loadoutRequest);
    }
    @GetMapping("/getLoadouts") public ResponseEntity<List<Loadout>> getLoadouts(){
        return ResponseEntity.ok().body(toAssembleLoadout.getLoadouts());
    }
}