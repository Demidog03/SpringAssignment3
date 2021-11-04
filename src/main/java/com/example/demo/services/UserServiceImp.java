package com.example.demo.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Loadout;
import com.example.demo.entities.Role;
import com.example.demo.helpers.ValidateHelper;
import com.example.demo.entities.User;
import com.example.demo.model.RoleRequest;
import com.example.demo.model.UserRequest;
import com.example.demo.repositories.LoadoutRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoadoutRepository productRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user==null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        else{
            log.info("User found with email: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    public Role saveRole(Role role) {
        return roleRepository.save(role);
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
    public void addRoleToUser(String email, String roleName){
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public User getUser(Integer id) {
        return userRepository.findById(id.longValue()).orElse(null);
    }

    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }


}
