package com.yesmine.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class RisqueHistorique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//columns
    private String num;
    private Double montantPrincipale;
    private Double soldePrincipale;
    private Double interetConventionnel;
    private Double soldeInteretConventionnel;
    private Double montantInteretRetard;
    private Double soldeInteretRetard;

    private String action;
    private String utilisateur;
    private Date dateAction;

    @ManyToOne
    @JoinColumn(name = "nature_id")
    private Nature nature;

    @ManyToOne
    @JoinColumn(name = "stade_id")
    private Stade stade;
}
