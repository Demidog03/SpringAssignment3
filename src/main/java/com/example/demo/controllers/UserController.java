package com.example.demo.controllers;

import com.example.demo.entities.Role;
import com.example.demo.helpers.ValidateHelper;
import com.example.demo.entities.User;
import com.example.demo.model.AddRoleRequest;
import com.example.demo.model.RoleRequest;
import com.example.demo.model.UserRequest;
import com.example.demo.model.AddLoadoutRequest;


import com.example.demo.services.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Transactional
@Slf4j
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
    @PostMapping("/role/create") public ResponseEntity<?> createRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }
    @PostMapping("/update") public void update(@RequestBody User user){
        userService.updateUser(user);

    }
    @GetMapping("/get/users") public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PostMapping("/delete") public Object delete(@RequestParam int id){
        boolean result = userService.deleteUser(id);
        if(result){
            return new ResponseEntity("user deleted", HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("user not found");
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
        return ResponseEntity.ok().body("loadouts added to user");
    }
    @PostMapping("/addRoles")
    public ResponseEntity<?> addRoles(@RequestBody AddRoleRequest role) {
        userService.addRoleToUser(role.getEmail(), role.getRoleName());
        return ResponseEntity.ok().build();
    }


}
