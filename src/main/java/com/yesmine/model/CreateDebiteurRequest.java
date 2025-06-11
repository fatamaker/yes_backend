package com.yesmine.model;


import lombok.Data;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class CreateDebiteurRequest {
    private Long numContentieux;
    private Date dateTransfert;
    @PositiveOrZero(message = "Le solde doit être positif ou zéro")
    private Double soldeRecouvrement;
    private String rapporteur;
    @NotNull(message = "L'ID de la personne est obligatoire")
    private Long personneId;
    private Long numeroDossier;
    
    
    
}
