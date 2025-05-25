package com.yesmine.service;

import com.yesmine.model.Operation;
import com.yesmine.model.Risque;
import com.yesmine.repository.OperationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    private final OperationRepository operationRepository;

    // Injection du repository via le constructeur
    @Autowired
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    // Créer une nouvelle opération
    public Operation createOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    // Récupérer toutes les opérations
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    // Récupérer une opération par son ID
    public Optional<Operation> getOperationById(Long id) {
        return operationRepository.findById(id);
    }

    // Mettre à jour une opération existante
    public Operation updateOperation(Long id, Operation operationDetails) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operation not found with id " + id));

        // Mettre à jour les champs de l'opération
        operation.setRisque(operationDetails.getRisque());
        operation.setDate(operationDetails.getDate());
        operation.setMatricule(operationDetails.getMatricule());
        operation.setEtatOperation(operationDetails.getEtatOperation());
        operation.setMontant(operationDetails.getMontant());
        operation.setType(operationDetails.getType());

        return operationRepository.save(operation);
    }

    // Supprimer une opération par son ID
    public void deleteOperation(Long id) {
        operationRepository.deleteById(id);
    }

    // Récupérer les opérations par Risque
    public List<Operation> getOperationsByRisque(Risque risque) {
        return operationRepository.findByRisque(risque);
    }
}
