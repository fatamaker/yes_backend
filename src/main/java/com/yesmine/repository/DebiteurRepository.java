package com.yesmine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yesmine.model.Debiteur;

import java.util.List;
import java.util.Optional;

public interface DebiteurRepository extends JpaRepository<Debiteur, Long> {
    // Méthode personnalisée pour trouver un débiteur par son numContentieux
    List<Debiteur> findByNumContentieux(Long numContentieux);

    // Méthode personnalisée pour trouver des débiteurs par rapporteur
    List<Debiteur> findByRapporteur(String rapporteur);
    List<Debiteur> findByPersonneNomContainingIgnoreCase(String nom);
    Optional<Debiteur> findByDossier_Numero(Long numero);
}
