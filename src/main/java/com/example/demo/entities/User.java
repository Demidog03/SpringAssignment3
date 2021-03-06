package com.example.demo.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="usr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String firstName;
    private String email;
    private String secondName;
    private int age;
    private String password;
    @ManyToMany
    private List<Loadout> loadouts;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public User(final Long id, final String firstName, final String secondName, final int age, final String email, final String password){
        this.firstName =firstName;
        this.secondName = secondName;
        this.email = email;
        this.age = age;
        this.password = password;
        this.id = id;
    }
    public User(final String firstName, final String secondName, final int age, final String email, final String password){
        this.firstName =firstName;
        this.secondName = secondName;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public User(final long id) {
        this.id = id;
    }
}