package com.example.demo.repositories;

import com.example.demo.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
