package com.example.demo.controllers;


import com.example.demo.entities.Role;
import com.example.demo.services.RoleServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleController {

    private RoleServices roleServices;

    @Autowired public RoleController(RoleServices roleServices) {
        this.roleServices = roleServices;
    }


    @PostMapping("/update") public void update(@RequestBody Role role){
        roleServices.updateRole(role);

    }
    @PostMapping("/delete") public Object delete(@RequestParam int id){
        boolean result = roleServices.deleteRole(id);
        if(result){
            return new ResponseEntity("role deleted", HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("role not found");
    }
}
