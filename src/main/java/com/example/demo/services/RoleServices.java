package com.example.demo.services;

import com.example.demo.entities.Role;
import com.example.demo.repositories.LoadoutRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoadoutRepository productRepository;
    @Autowired
    private RoleRepository roleRepository;




    public boolean deleteRole(@RequestParam int id) {
        if(roleRepository.existsById((long) id)){

            roleRepository.deleteById((long) id);
            return true;
        }

        return false;
    }

    public Role updateRole(Role role) {
        Role existingRole = roleRepository.findById(role.getId()).orElse(null);
        existingRole.setName(role.getName());

        return roleRepository.save(existingRole);

    }
}
