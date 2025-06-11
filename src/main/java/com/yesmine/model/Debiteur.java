package com.yesmine.model;



import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Debiteur {

    @Id
    @Column(name = "num_contentieux")
    private Long numContentieux;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_transfert")
    private Date dateTransfert;

    @Column(name = "solde_recouvrement")
    private Double soldeRecouvrement;

 

    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "etat_id", nullable = false) // Correction ici private
	 * Etat etats;
	 */
    
    //numero de dossier
   
    
    @Column(nullable = true)  // Rend le champ optionnel en base
    private String rapporteur;
    
    @ManyToOne
    @JoinColumn(name = "dossier_id", nullable = true)  // Relation optionnelle
    private Dossier dossier;

 
    

    public Debiteur() {}

    public Debiteur(Long numContentieux, Date dateTransfert, Double soldeRecouvrement, String rapporteur, Personne personne) {
        this.numContentieux = numContentieux;
        this.dateTransfert = dateTransfert;
        this.soldeRecouvrement = soldeRecouvrement;
        this.rapporteur = rapporteur;
        this.personne = personne;
        }
}
