package com.yesmine.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

   

    public Type() {}

    public Type(String libelle, Double frais) {
        this.libelle = libelle;
        
    }
}

