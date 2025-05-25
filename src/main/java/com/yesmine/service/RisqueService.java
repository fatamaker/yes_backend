package com.yesmine.service;


import com.yesmine.model.Risque;
import com.yesmine.model.RisqueHistorique;
import com.yesmine.repository.RisqueHistoriqueRepository;
import com.yesmine.repository.RisqueRepository;

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

    // Récupérer un risque par ID
    public Optional<Risque> getRisqueById(Long id) {
        return risqueRepository.findById(id);
    }

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
}
