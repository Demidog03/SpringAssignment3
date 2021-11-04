package com.example.demo.services;

import com.example.demo.entities.Loadout;
import com.example.demo.model.LoadoutRequest;
import com.example.demo.repositories.LoadoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
@Service
public class ToAssembleLoadout {

    @Autowired
    LoadoutRepository loadoutRepository;

    public Loadout createLoadout(LoadoutRequest request) {
        request.setDate(new Date());
        return loadoutRepository.save(new Loadout(request));
    }
    public boolean deleteLoadout(@RequestParam int id){
        if (loadoutRepository.existsById((long) id)){
            loadoutRepository.deleteById((long) id);
            return true;
        }
        return false;
    }
    public Loadout updateLoadout(Loadout loadout){
        Loadout existingLoadout = loadoutRepository.findById(loadout.getId()).orElse(null);
        existingLoadout.setLoadoutName(loadout.getLoadoutName());
        existingLoadout.setDate(new Date());
        existingLoadout.setPrimaryWeapon(loadout.getPrimaryWeapon());
        existingLoadout.setSecondaryWeapon(loadout.getSecondaryWeapon());
        existingLoadout.setTotalPrice(loadout.getTotalPrice());
        return loadoutRepository.save(existingLoadout);
    }
    public Loadout getLoadout(@RequestParam int id){
        return loadoutRepository.findById((long) id).orElse(null);
    }
}
