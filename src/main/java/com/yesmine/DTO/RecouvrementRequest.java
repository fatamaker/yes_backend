package com.yesmine.DTO;

public class RecouvrementRequest {
    private Double montant;
    private Long dossierId;
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public Long getDossierId() {
		return dossierId;
	}
	public void setDossierId(Long dossierId) {
		this.dossierId = dossierId;
	}

   
}

