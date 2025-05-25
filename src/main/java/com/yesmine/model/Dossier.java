package com.yesmine.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
public class Dossier {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero; 
	private String etat;
	
	   @OneToMany(mappedBy = "dossier")
	    @JsonManagedReference
	private List<Risque> risques = new ArrayList<>();
	


}
