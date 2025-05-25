package com.yesmine.DTO;

import lombok.Data;

import java.util.Date;



@Data
public class DebiteurDto {

    private Long numContentieux;
    private Date dateTransfert;
    private Double soldeRecouvrement;
    private String rapporteur;
    private Long numeroDossier;
    private PersonneDto personne;
   
    

}
