package com.example.demo.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="usr")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(columnDefinition = "TEXT")
    private String firstName;
    private String email;
    private String secondName;
    @ManyToMany
    private List<Loadout> loadouts;

    public User(final String firstName, final String secondName, final String email){
        this.firstName =firstName;
        this.secondName = secondName;
        this.email = email;
    }
}
