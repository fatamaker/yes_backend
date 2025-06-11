package com.yesmine.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class RecouvrementRisque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montantAffecte;
    
    
    @ManyToOne
    @JsonBackReference // Ajoutez cette annotation
    @JoinColumn(name = "recouvrement_id", nullable = false)
    private Recouvrement recouvrement;

   

    @ManyToOne
    @JoinColumn(name = "risque_id", nullable = false)
    private Risque risque;

    public RecouvrementRisque() {}
}
