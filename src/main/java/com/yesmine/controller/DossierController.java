package com.yesmine.controller;

import com.yesmine.model.Dossier;
import com.yesmine.model.Risque;
import com.yesmine.service.DossierService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
public class DossierController {

    private final DossierService dossierService;

    public DossierController(DossierService dossierService) {
        this.dossierService = dossierService;
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
   
    public ResponseEntity<Dossier> createDossier(@RequestBody Dossier dossier) {
        Dossier created = dossierService.createDossier(dossier);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public List<Dossier> getAllDossiers() {
        return dossierService.getAllDossiers();
    }
    
    @GetMapping("/{numero}/risques")
    public ResponseEntity<List<Risque>> getRisquesByDossier(@PathVariable Long numero) {
        List<Risque> risques = dossierService.getRisquesByDossierNumero(numero);
        if (risques != null) {
            return ResponseEntity.ok(risques);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
