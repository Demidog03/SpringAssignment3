package com.example.demo.services;

import com.example.demo.model.UserRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserServices {
    boolean createUser(UserRequest user);
    boolean updateUser(UserRequest user);
    boolean logout(String token);
    String getToken(String email);
    String getEmailByToken(String token);
    String login(UserRequest user);
    Map<String, UserRequest> getUsers();
    Map<String, String> getTokens();
    Map<String, String> getBlackListTokens();
    UserRequest getUser(int id);
    UserRequest getUser(String email);
    UserRequest findById(int id);
    UserRequest findByEmail(String email);

    ResponseEntity delete(Integer id, String token);
}

