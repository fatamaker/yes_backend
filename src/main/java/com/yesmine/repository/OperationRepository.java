package com.yesmine.repository;

import com.yesmine.model.Operation;
import com.yesmine.model.Risque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    
    // Méthode pour récupérer toutes les opérations liées à un Risque
    List<Operation> findByRisque(Risque risque);
}
