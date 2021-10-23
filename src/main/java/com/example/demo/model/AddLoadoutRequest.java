package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class AddLoadoutRequest {
    private Integer userId;
    private List<Integer> loadoutsIds;
}
