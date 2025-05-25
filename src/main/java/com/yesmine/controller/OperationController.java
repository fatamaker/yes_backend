package com.yesmine.controller;


import com.yesmine.model.Operation;
import com.yesmine.model.Risque;
import com.yesmine.service.OperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    private final OperationService operationService;

    // Injection du service
    
    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    // Endpoint pour récupérer toutes les opérations
    
    @GetMapping
    public List<Operation> getAllOperations() {
        return operationService.getAllOperations();
    }

    // Endpoint pour créer une nouvelle opération
    
    @PostMapping
    public ResponseEntity<Operation> createOperation(@RequestBody Operation operation) {
        if (operation.getRisque() == null || operation.getMatricule() == null || operation.getEtatOperation() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Operation savedOperation = operationService.createOperation(operation);
        return ResponseEntity.ok(savedOperation);
    }

    // Endpoint pour récupérer une opération par son ID
    
    @GetMapping("/{id}")
    public ResponseEntity<Operation> getOperationById(@PathVariable Long id) {
        Operation operation = operationService.getOperationById(id)
                .orElseThrow(() -> new RuntimeException("Operation not found with id " + id));
        return ResponseEntity.ok(operation);
    }

    // **Nouveau endpoint pour récupérer les opérations par Risque**
    
    @GetMapping("/risque/{risqueId}")
    public ResponseEntity<List<Operation>> getOperationsByRisque(@PathVariable Long risqueId) {
        Risque risque = new Risque();
        risque.setId(risqueId);
        List<Operation> operations = operationService.getOperationsByRisque(risque);
        return ResponseEntity.ok(operations);
    }

    // Endpoint pour mettre à jour une opération
    
    @PutMapping("/{id}")
    public ResponseEntity<Operation> updateOperation(@PathVariable Long id, @RequestBody Operation operationDetails) {
        Operation updatedOperation = operationService.updateOperation(id, operationDetails);
        return ResponseEntity.ok(updatedOperation);
    }

    // Endpoint pour supprimer une opération
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        operationService.deleteOperation(id);
        return ResponseEntity.noContent().build();
    }
}
