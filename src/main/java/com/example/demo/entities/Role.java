package com.example.demo.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    public Role(final String name){
        this.name = name;
    }
}