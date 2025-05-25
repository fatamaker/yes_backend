package com.yesmine.controller;

import com.yesmine.service.DossierService;
import com.yesmine.service.RisqueService;
import com.yesmine.model.Dossier;
import com.yesmine.model.Risque;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risques")
public class RisqueController {

    private final RisqueService risqueService;
    
    private final DossierService dossierService;

    public RisqueController(RisqueService risqueService, DossierService dossierService) {
        this.risqueService = risqueService;
        this.dossierService=dossierService;
    }

    // Récupérer tous les risques
    @GetMapping
    public List<Risque> getAllRisques() {
        return risqueService.getAllRisques();
    }

    @PostMapping("/{dossierId}")
    public ResponseEntity<Risque> createRisque(
            @PathVariable Long dossierId,
            @RequestBody Risque risque) {

        // Cherche le dossier par ID

        Dossier dossier = dossierService.getByNumero(dossierId);
        if (dossier == null) {
            return ResponseEntity.notFound().build();
        }

        // Lie le risque au dossier
        risque.setDossier(dossier);

        // Sauvegarde
        Risque savedRisque = risqueService.saveRisque(risque);

        return ResponseEntity.ok(savedRisque);
    }


    // Récupérer un risque par son numéro (num)
    @GetMapping("/{num}")
    public ResponseEntity<Risque> getRisqueByNum(@PathVariable String num) {
        Risque risque = risqueService.getRisqueByNum(num);
        if (risque != null) {
            return ResponseEntity.ok(risque);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mettre à jour un risque
    @PutMapping("/{id}")
    public ResponseEntity<Risque> updateRisque(@PathVariable Long id, @RequestBody Risque risque) {
        Risque existing = risqueService.getAllRisques()
                                       .stream()
                                       .filter(r -> r.getId().equals(id))
                                       .findFirst()
                                       .orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        risque.setId(id); // Assure-toi que l'ID est conservé
        Risque updated = risqueService.updateRisque(risque);
        return ResponseEntity.ok(updated);
    }

    // Supprimer un risque
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRisque(@PathVariable Long id) {
        risqueService.deleteRisque(id);
        return ResponseEntity.noContent().build();
    }
}
