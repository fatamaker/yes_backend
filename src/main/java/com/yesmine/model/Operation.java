package com.yesmine.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class Operation {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément
    private Long id;

    // Clé étrangère vers l’entité Risque (relation ManyToOne)
    @ManyToOne
    @JoinColumn(name = "risque_id", nullable = false) 
    private Risque risque; 

    @Temporal(TemporalType.DATE)
    @Column(name = "date") 
    private Date date; // Date de l'opération

    @Column(name = "matricule") 
    private String matricule; // Matricule de l’utilisateur

    @Column(name = "etat_operation")
    private String etatOperation; // État de l’opération

    @Column(name = "montant") 
    private Double montant; // Montant de l’opération

    @ManyToOne // Clé étrangère vers l’entité Type
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    
    public Operation() {}

    // Constructeur avec paramètres
    public Operation(Risque risque, Date date, String matricule, String etatOperation, Double montant, Type type) {
        this.risque = risque;
        this.date = date;
        this.matricule = matricule;
        this.etatOperation = etatOperation;
        this.montant = montant;
        this.type = type;
    }
}
