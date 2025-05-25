package com.yesmine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yesmine.model.Risque;

@Repository
public interface RisqueRepository extends JpaRepository<Risque, Long> {
    
    // Méthode pour récupérer un Risque par son numéro unique
    Risque findByNum(String num);
}
