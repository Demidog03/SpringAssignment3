package com.example.demo.controllers;

import com.example.demo.helpers.ValidateHelper;
import com.example.demo.model.User;
import com.example.demo.model.UserRequest;
import com.example.demo.model.AddLoadoutRequest;


import com.example.demo.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserServiceImp userService;

    @Autowired public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @PostMapping("/create") public ResponseEntity create(@RequestBody UserRequest userRequest) {
        boolean result = userService.saveUser(userRequest);
        if (result) {
            return new ResponseEntity("user created", HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body("bad request");
    }

    @GetMapping("/get") public ResponseEntity getUserById(@RequestParam Integer id) {
        User userRequest = userService.getUser(id);
        if (userRequest == null) {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userRequest);
    }

    @GetMapping("/email") public ResponseEntity checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(ValidateHelper.validate(email));
    }

    @PostMapping("/addLoadouts")
    public ResponseEntity addProducts(@RequestBody AddLoadoutRequest request) {
        userService.addLoadoutsToUser(request.getUserId(), request.getLoadoutsIds());
        return ResponseEntity.ok().body("");
    }
}
