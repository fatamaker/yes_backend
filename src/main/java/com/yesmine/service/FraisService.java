package com.yesmine.service;

import com.yesmine.model.*;
import com.yesmine.repository.FraisRepository;
import com.yesmine.repository.OperationRepository;
import com.yesmine.repository.RisqueRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FraisService {
	
	@Autowired
	private RisqueRepository risqueRepository;


    private final FraisRepository fraisRepository;
    private final OperationRepository operationRepository;

    public FraisService(FraisRepository fraisRepository, OperationRepository operationRepository) {
        this.fraisRepository = fraisRepository;
        this.operationRepository = operationRepository;
    }

    // Créer une demande de frais
    public Frais createFrais(Frais frais) {
        frais.setEtat(EtatFrais.EN_ATTENTE);
        frais.setDateDemande(new Date());
        return fraisRepository.save(frais);
    }

    // Lister tous les frais
    public List<Frais> getAllFrais() {
        return fraisRepository.findAll();
    }

    @Transactional
    public Operation validerFrais(Long fraisId) {
        Frais frais = fraisRepository.findById(fraisId)
                .orElseThrow(() -> new RuntimeException("Frais introuvable"));

        if (frais.getEtat() != EtatFrais.EN_ATTENTE) {
            throw new RuntimeException("Ce frais est déjà traité !");
        }

        // Marquer le frais comme validé
        frais.setEtat(EtatFrais.VALIDE);
        fraisRepository.save(frais);

        // Créer une opération
        Operation operation = new Operation(
                frais.getRisque(),
                new Date(),
                frais.getMatricule(),
                "VALIDÉ",
                frais.getMontant(),
                frais.getType()
        );
        operationRepository.save(operation);

        // Ajouter le montant de l'opération au montantPrincipale du Risque
        Risque risque = frais.getRisque();
        double nouveauMontant = (risque.getMontantPrincipale() != null ? risque.getMontantPrincipale() : 0.0) + frais.getMontant();
        risque.setMontantPrincipale(nouveauMontant);

        risqueRepository.save(risque); // Sauvegarde du risque mis à jour

        return operation;
    }

    // Rejeter un frais
    public Frais rejeterFrais(Long fraisId) {
        Frais frais = fraisRepository.findById(fraisId)
                .orElseThrow(() -> new RuntimeException("Frais introuvable"));

        if (frais.getEtat() != EtatFrais.EN_ATTENTE) {
            throw new RuntimeException("Ce frais est déjà traité !");
        }

        frais.setEtat(EtatFrais.REJETE);
        return fraisRepository.save(frais);
    }
}
