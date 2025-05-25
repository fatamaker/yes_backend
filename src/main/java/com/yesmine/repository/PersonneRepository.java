package com.yesmine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yesmine.model.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
    // Méthode personnalisée pour trouver une personne par son CIN
    Personne findByCin(String cin);
}
