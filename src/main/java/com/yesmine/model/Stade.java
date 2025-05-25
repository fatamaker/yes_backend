package com.yesmine.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Stade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", unique = true, nullable = false)
    private String nom;

    public Stade() {}

    public Stade(String nom) {
        this.nom = nom;
    }

    public Stade(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return null;
    }
}
