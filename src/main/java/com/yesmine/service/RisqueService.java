package com.yesmine.service;


import com.yesmine.model.Risque;
import com.yesmine.model.RisqueHistorique;
import com.yesmine.model.Stade;
import com.yesmine.repository.RisqueHistoriqueRepository;
import com.yesmine.repository.RisqueRepository;
import com.yesmine.repository.StadeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RisqueService {

    @Autowired
    private RisqueRepository risqueRepository;

    @Autowired
    private RisqueHistoriqueRepository historiqueRisqueRepository;
    
    @Autowired
    private StadeRepository stadeRepository;


    // Créer un nouveau risque
    public Risque saveRisque(Risque risque) {
        Risque saved = risqueRepository.save(risque);
        enregistrerHistorique(saved, "CREATION");
        return saved;
    }

	    // Mettre à jour un risque existant
	    public Risque updateRisque(Risque risque) {
	        Risque updated = risqueRepository.save(risque);
	        enregistrerHistorique(updated, "MODIFICATION");
	        return updated;
	    }

    // Supprimer un risque par ID
    public void deleteRisque(Long id) {
        Risque risque = risqueRepository.findById(id).orElse(null);
        if (risque != null) {
            risqueRepository.delete(risque);
            enregistrerHistorique(risque, "SUPPRESSION");
        }
    }

    // Récupérer tous les risques
    public List<Risque> getAllRisques() {
        return risqueRepository.findAll();
    }

    // Récupérer un risque par son numéro
    public Risque getRisqueByNum(String num) {
        return risqueRepository.findByNum(num);
    }

	/*
	 * // Récupérer un risque par ID public Optional<Risque> getRisqueById(Long id)
	 * { return risqueRepository.findById(id); }
	 */

    // Récupérer les risques associés à un débiteur (natureId)
    
    public Optional<Risque> getRisqueByDebiteur(Long natureId) {
        return risqueRepository.findById(natureId);    // Remplacer natureId par debiteurId si nécessaire
    }

    
    // Méthode privée pour enregistrer l'historique
    private void enregistrerHistorique(Risque risque, String action) {
        RisqueHistorique historique = new RisqueHistorique();
        historique.setNum(risque.getNum());
        historique.setMontantPrincipale(risque.getMontantPrincipale());
        historique.setSoldePrincipale(risque.getSoldePrincipale());
        historique.setInteretConventionnel(risque.getInteretConventionnel());
        historique.setSoldeInteretConventionnel(risque.getSoldeInteretConventionnel());
        historique.setMontantInteretRetard(risque.getMontantInteretRetard());
        historique.setSoldeInteretRetard(risque.getSoldeInteretRetard());
        historique.setNature(risque.getNature());
        historique.setStade(risque.getStade());
        historique.setAction(action);
        historique.setUtilisateur("admin"); // ou récupérer l'utilisateur connecté
        historique.setDateAction(new Date());

        historiqueRisqueRepository.save(historique);
    }
    
    
 
    public Risque getRisqueById(Long id) {
        Optional<Risque> optionalRisque = risqueRepository.findById(id);
        return optionalRisque.orElse(null);
    }
    
    public List<Risque> getRisksReadyForClosure() {
        return risqueRepository.findRisksReadyForClosure();
    }

	/*
	 * @Transactional public Risque closeRisk(Long id) { Risque risque =
	 * risqueRepository.findById(id) .orElseThrow(() -> new
	 * EntityNotFoundException("Risk not found with id: " + id));
	 * 
	 * if (risque.getMontantPrincipale() != 0) { throw new
	 * IllegalStateException("Cannot close risk with non-zero principal balance"); }
	 * 
	 * if (hasUnpaidFees(risque)) { throw new
	 * IllegalStateException("Cannot close risk with unpaid fees"); }
	 * 
	 * Stade closedStage = stadeRepository.findByNom("prés-clôture") .orElseThrow(()
	 * -> new EntityNotFoundException("Closed stage not found"));
	 * 
	 * 
	 * risque.setStade(closedStage); Risque updatedRisque =
	 * risqueRepository.save(risque); enregistrerHistorique(updatedRisque,
	 * "Clôturé");
	 * 
	 * return updatedRisque; }
	 */
    
    
    @Transactional
    public Risque closeRisk(Long id) {
        Risque risque = risqueRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Risk not found with id: " + id));
        
        System.out.println("Risque avant update: stade = " + (risque.getStade() != null ? risque.getStade().getNom() : "null"));
        
        if (risque.getMontantPrincipale() != 0) {
            throw new IllegalStateException("Cannot close risk with non-zero principal balance");
        }
        
        if (hasUnpaidFees(risque)) {
            throw new IllegalStateException("Cannot close risk with unpaid fees");
        }
        
        Stade closedStage = stadeRepository.findByNom("Clôturé")
        	    .orElseThrow(() -> new EntityNotFoundException("Closed stage not found"));
        
        System.out.println("Stade récupéré: " + closedStage.getNom());
        
        risque.setStade(closedStage);
        
        // Option 1 : save explicite
        Risque updatedRisque = risqueRepository.save(risque);
        
        enregistrerHistorique(updatedRisque, "Clôturé");
        
        System.out.println("Risque après update: stade = " + updatedRisque.getStade().getNom());
        
        return updatedRisque;
    }

    private boolean hasUnpaidFees(Risque risque) {
        return risqueRepository.hasUnpaidFees(risque);
    }}