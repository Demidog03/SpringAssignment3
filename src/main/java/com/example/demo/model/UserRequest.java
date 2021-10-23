package com.example.demo.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequest {
    private int id;
    private String firstName;
    private String email;
    private String secondName;
    private String password;
    private int age;


}
