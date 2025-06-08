package com.yesmine.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Frais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Montant du frais demandé
    @Column(nullable = false)
    private Double montant;

    // Date de la demande
    @Temporal(TemporalType.DATE)
    private Date dateDemande;

    // Matricule de l'utilisateur ayant créé le frais
    private String matricule;

    // État de validation du frais (en attente, validé, rejeté)
    @Enumerated(EnumType.STRING)
    private EtatFrais etat = EtatFrais.EN_ATTENTE;

    // Lien vers le type d’opération
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    // Lien vers le risque concerné
    @ManyToOne
    @JoinColumn(name = "risque_id", nullable = false)
    private Risque risque;

    public Frais() {}

    public Frais(Double montant, Date dateDemande, String matricule, Type type, Risque risque) {
        this.montant = montant;
        this.dateDemande = dateDemande;
        this.matricule = matricule;
        this.type = type;
        this.risque = risque;
        this.etat = EtatFrais.EN_ATTENTE;
    }
}
