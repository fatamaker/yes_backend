package com.yesmine.controller;

import com.yesmine.model.Personne;
import com.yesmine.service.PersonneService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    private final PersonneService personneService;

    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    // Récupérer toutes les personnes
    @GetMapping
    public List<Personne> getAllPersonnes() {
        return personneService.getAllPersonnes();
    }

    // Créer une nouvelle personne
    @PostMapping
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne) {
        if (personne.getNom() == null || personne.getNom().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Personne savedPersonne = personneService.savePersonne(personne);
        return ResponseEntity.ok(savedPersonne);
    }

    // Récupérer une personne par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable Long id) {
        Personne personne = personneService.getPersonneById(id);
        if (personne == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personne);
    }

    // Supprimer une personne par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return ResponseEntity.noContent().build();
    }
}
