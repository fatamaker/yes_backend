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

    @PutMapping("/{id}")
    public ResponseEntity<Risque> updateRisque(@PathVariable Long id, @RequestBody Risque risque) {
        // Récupérer le risque existant
        Risque existing = risqueService.getAllRisques()
                                       .stream()
                                       .filter(r -> r.getId().equals(id))
                                       .findFirst()
                                       .orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Garder les champs que tu ne veux pas changer
        risque.setId(id);
        risque.setDossier(existing.getDossier()); // dossier_id
        risque.setNature(existing.getNature());   // nature_id
        risque.setStade(existing.getStade());     // stade_id
        risque.setNum(existing.getNum());         // num

        // Ici on met à jour seulement les autres champs du DTO reçu
        // Par exemple :
        existing.setInteretConventionnel(risque.getInteretConventionnel());
        existing.setMontantInteretRetard(risque.getMontantInteretRetard());
        existing.setMontantPrincipale(risque.getMontantPrincipale());
        existing.setSoldeInteretConventionnel(risque.getSoldeInteretConventionnel());
        existing.setSoldeInteretRetard(risque.getSoldeInteretRetard());
        existing.setSoldePrincipale(risque.getSoldePrincipale());
        
        // Sauvegarder les modifications
        Risque updated = risqueService.updateRisque(existing);

        return ResponseEntity.ok(updated);
    }

    
    @GetMapping(params = "id")
    public ResponseEntity<Risque> getRisqueByIdParam(@RequestParam Long id) {
        Risque risque = risqueService.getRisqueById(id);
        if (risque != null) {
            return ResponseEntity.ok(risque);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    
    

}
