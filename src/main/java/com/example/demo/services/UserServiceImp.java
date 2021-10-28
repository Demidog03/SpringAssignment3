package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Loadout;
import com.example.demo.helpers.ValidateHelper;
import com.example.demo.entities.User;
import com.example.demo.model.UserRequest;
import com.example.demo.repositories.LoadoutRepository;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserServiceImp {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoadoutRepository productRepository;

    public boolean saveUser(UserRequest userRequest) {
        if(!ValidateHelper.validate(userRequest.getEmail())) {
            return false;
        }
        User byEmail = userRepository.findByEmail(userRequest.getEmail());
        if(byEmail != null) {
            return false;
        }
        User user = new User(userRequest.getFirstName(), userRequest.getSecondName(), userRequest.getAge(),
                userRequest.getEmail(), userRequest.getPassword());
        userRepository.save(user);
        return true;
    }

    public User updateUser(User user) {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            existingUser.setFirstName(user.getFirstName());
            existingUser.setSecondName(user.getSecondName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAge(user.getAge());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);

        }



    public boolean deleteUser(@RequestParam int id) {
        if(userRepository.existsById((long) id)){

            userRepository.deleteById((long) id);
            return true;
        }

        return false;
    }


    public boolean addLoadoutsToUser(Integer userId, List<Integer> productIds) {
        Optional<User> userOptional = userRepository.findById(userId.longValue());
        User user = userOptional.orElse(null);
        if(user == null) {
            return false;
        }
        List<Loadout> loadouts = new ArrayList<>();
        productIds.forEach(id -> productRepository.findById(id.longValue()).ifPresent(p -> loadouts.add(p)));
        user.setLoadouts(loadouts);
        userRepository.save(user);
        return true;
    }

    public User getUser(Integer id) {
        return userRepository.findById(id.longValue()).orElse(null);
    }

}
