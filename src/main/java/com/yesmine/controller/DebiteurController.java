package com.yesmine.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yesmine.DTO.DebiteurDto;
import com.yesmine.model.CreateDebiteurRequest;
import com.yesmine.model.Debiteur;
import com.yesmine.model.Dossier;
import com.yesmine.model.Personne;
import com.yesmine.service.DebiteurService;
import com.yesmine.service.DossierService;
import com.yesmine.service.PersonneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/debiteurs")
@RequiredArgsConstructor
public class DebiteurController {

    private final DebiteurService debiteurService;
    private final PersonneService personneService;
    private final DossierService dossierService;


    // Créer un débiteur
    @PostMapping
    public ResponseEntity<DebiteurDto> créerDebiteur(@RequestBody Debiteur debiteur) {
        Debiteur saved = debiteurService.enregistrer(debiteur);
        DebiteurDto dto = debiteurService.mapToDTO(saved);  
        return ResponseEntity.ok(dto);
    }

    // Récupérer les débiteurs par leur numéro de contentieux
    @GetMapping("/contentieux/{numContentieux}")
    public ResponseEntity<List<DebiteurDto>> getParNumContentieux(@PathVariable Long numContentieux) {
        List<DebiteurDto> debiteurs = debiteurService.trouverParNumContentieux(numContentieux);
        return debiteurs.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(debiteurs);
    }

    // Récupérer les débiteurs par rapporteur
    @GetMapping("/rapporteur/{rapporteur}")
    public ResponseEntity<List<DebiteurDto>> getParRapporteur(@PathVariable String rapporteur) {
        List<DebiteurDto> debiteurs = debiteurService.trouverParRapporteur(rapporteur);
        return debiteurs.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(debiteurs);
    }

    // Lister tous les débiteurs
    @GetMapping
    public ResponseEntity<List<DebiteurDto>> listerTous() {
        List<DebiteurDto> debiteurs = debiteurService.listerTous();
        return ResponseEntity.ok(debiteurs);
    }
    
    
    @GetMapping("/search")
    public ResponseEntity<List<DebiteurDto>> searchDebiteurs(
        @RequestParam(required = false) String nom
    ) {
        List<Debiteur> debiteurs = debiteurService.searchByNom(nom);
        List<DebiteurDto> dtos = debiteurs.stream()
            .map(debiteurService::mapToDTO)
            .toList();
        return ResponseEntity.ok(dtos);
    }
    

    @PostMapping("/avec-personne-existante")
    public ResponseEntity<Debiteur> creerDebiteurAvecPersonneExistante(@RequestBody CreateDebiteurRequest request) {
        Personne personne = personneService.getPersonneById(request.getPersonneId());
        if (personne == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Dossier dossier = dossierService.getByNumero(request.getNumeroDossier());
        if (dossier == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Debiteur debiteur = new Debiteur();
        debiteur.setNumContentieux(request.getNumContentieux());
        debiteur.setDateTransfert(request.getDateTransfert());
        debiteur.setSoldeRecouvrement(request.getSoldeRecouvrement());
        debiteur.setRapporteur(request.getRapporteur());
        debiteur.setPersonne(personne);
        debiteur.setDossier(dossier);

        Debiteur saved = debiteurService.enregistrer(debiteur);

        return ResponseEntity.ok(saved);  // Retourne l’entité Debiteur
    }
    
    @GetMapping("/by-dossier/{numero}")
    public ResponseEntity<Debiteur> getDebiteurByDossierNumero(@PathVariable Long numero) {
        return debiteurService.getDebiteurByDossierNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{debiteurId}/assign-reporter")
    public ResponseEntity<Debiteur> assignReporter(
            @PathVariable Long debiteurId,
            @RequestBody Map<String, String> request) {
        
        String nomRapporteur = request.get("rapporteur");
        Debiteur updatedDebiteur = debiteurService.assignReporter(debiteurId, nomRapporteur);
        return ResponseEntity.ok(updatedDebiteur);
    }



}
