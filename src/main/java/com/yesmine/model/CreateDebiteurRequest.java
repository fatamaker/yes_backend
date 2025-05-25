package com.yesmine.model;


import lombok.Data;

import java.util.Date;

@Data
public class CreateDebiteurRequest {
    private Long numContentieux;
    private Date dateTransfert;
    private Double soldeRecouvrement;
    private String rapporteur;
    private Long personneId;
    private Long numeroDossier;
}
