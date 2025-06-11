package com.yesmine.repository;

import com.yesmine.model.Stade;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadeRepository extends JpaRepository<Stade, Long> {

	Optional<Stade> findByNom(String nom);
    
    // Vérifier si un stade existe par nom
    boolean existsByNom(String nom);
}