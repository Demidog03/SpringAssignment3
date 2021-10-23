package com.example.demo.repositories;

import com.example.demo.model.User;
import com.example.demo.model.UserRequest;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByFirstNameAndSecondName(String firstName, String secondName);
}
