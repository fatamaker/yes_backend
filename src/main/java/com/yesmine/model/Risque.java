package com.yesmine.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Risque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num", unique = true, nullable = false)
    private String num;//int

    @Column(name = "montant_principale")
    private Double montantPrincipale; //entree

    @Column(name = "solde_principale")
    private Double soldePrincipale;

    @Column(name = "interet_conventionnel")
    private Double interetConventionnel; // Nouveau champ entree

    @Column(name = "solde_interet_conventionnel")
    private Double soldeInteretConventionnel; // Nouveau champ

    @Column(name = "montant_interet_retard")
    private Double montantInteretRetard; // Nouveau champ

    @Column(name = "solde_interet_retard")
    private Double soldeInteretRetard; // Nouveau champ
    
    //debiteur

    @ManyToOne
    @JoinColumn(name = "nature_id", nullable = false)
    private Nature nature;

    @ManyToOne
    @JoinColumn(name = "stade_id", nullable = false)
    private Stade stade;
    
    //numero de dossier

    @ManyToOne
    @JoinColumn(name = "dossier_id", nullable = false)
    @JsonIgnoreProperties({"risques"}) 
    @JsonBackReference// éviter la récursion si Dossier a une liste de risques
    private Dossier dossier;
    
    


    public Risque() {}

    public Risque(Long id, String num, Double montantPrincipale, Double soldePrincipale, 
                  Double interetConventionnel, Double soldeInteretConventionnel,
                  Double montantInteretRetard, Double soldeInteretRetard,
                  Nature nature, Stade stade) {
        super();
        this.id = id;
        this.num = num;
        this.montantPrincipale = montantPrincipale;
        this.soldePrincipale = soldePrincipale;
        this.interetConventionnel = interetConventionnel;
        this.soldeInteretConventionnel = soldeInteretConventionnel;
        this.montantInteretRetard = montantInteretRetard;
        this.soldeInteretRetard = soldeInteretRetard;
        this.nature = nature;
        this.stade = stade;
    }
}
