package com.yesmine.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Data
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance")
    private Date dateNaissance;

    @Column(nullable = false, unique = true)
    private String cin;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_personne", nullable = false)
    private TypePersonne typePersonne;

    @Column(name = "raison_sociale")
    private String raisonSociale;
    
    @Column(name = "is_debiteur", nullable = false)
    @JsonProperty("isDebiteur") // Force this name in JSON
    private boolean isDebiteur = false;
   
   

    public Personne() {}

    public Personne(String nom, String prenom, Date dateNaissance, String cin, 
                  TypePersonne typePersonne, String raisonSociale, boolean isDebiteur) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.cin = cin;
        this.typePersonne = typePersonne;
        this.raisonSociale = raisonSociale;
        this.isDebiteur = isDebiteur;
    }
}
