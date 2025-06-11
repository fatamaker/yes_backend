package com.yesmine.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
public class Recouvrement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    private LocalDate dateVersement;

    @Enumerated(EnumType.STRING)
    private EtatRecouvrement etat = EtatRecouvrement.EN_ATTENTE;

    @ManyToOne
    @JoinColumn(name = "dossier_id", nullable = false)
    private Dossier dossier;

    @OneToMany(mappedBy = "recouvrement", cascade = CascadeType.ALL)
    @JsonManagedReference // Ajoutez cette annotation
    private List<RecouvrementRisque> repartitions = new ArrayList<>();

    public Recouvrement() {}
}
