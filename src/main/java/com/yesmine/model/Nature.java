package com.yesmine.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Nature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", unique = true, nullable = false)
    private String nom;

    public Nature() {}

    public Nature(String nom) {
        this.nom = nom;
    }

    // Constructeur avec id pour désérialisation uniquement
    public Nature(Long id) {
        this.id = id;
    }
}

