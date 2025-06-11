package com.yesmine.service;
import com.yesmine.model.Dossier;
import com.yesmine.model.EtatRecouvrement;
import com.yesmine.model.Frais;
import com.yesmine.model.Recouvrement;
import com.yesmine.model.RecouvrementRisque;
import com.yesmine.model.Risque;
import com.yesmine.model.RisqueHistorique;
import com.yesmine.model.Stade;
import com.yesmine.repository.DossierRepository;
import com.yesmine.repository.RecouvrementRepository;
import com.yesmine.repository.RecouvrementRisqueRepository;
import com.yesmine.repository.RisqueHistoriqueRepository;
import com.yesmine.repository.RisqueRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecouvrementService {

    private final RecouvrementRepository recouvrementRepository;
    private final RecouvrementRisqueRepository recouvrementRisqueRepository;
    private final DossierRepository dossierRepository;
    private final RisqueRepository risqueRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Recouvrement createRecouvrement(Double montant, Long dossierId) {
        Dossier dossier = dossierRepository.findById(dossierId)
            .orElseThrow(() -> new RuntimeException("Dossier non trouvé"));

        Recouvrement recouvrement = new Recouvrement();
        recouvrement.setMontant(montant);
        recouvrement.setDateVersement(LocalDate.now());
        recouvrement.setEtat(EtatRecouvrement.EN_ATTENTE);
        recouvrement.setDossier(dossier);

        return recouvrementRepository.save(recouvrement);
    }

	/*
	 * public Recouvrement validerRecouvrement(Long recouvrementId, Map<Long,
	 * Double> repartitionMontants) { Recouvrement rec =
	 * recouvrementRepository.findById(recouvrementId) .orElseThrow(() -> new
	 * RuntimeException("Recouvrement non trouvé"));
	 * 
	 * if (!rec.getEtat().equals(EtatRecouvrement.EN_ATTENTE)) { throw new
	 * IllegalStateException("Ce recouvrement a déjà été traité"); }
	 * 
	 * double totalReparti =
	 * repartitionMontants.values().stream().mapToDouble(Double::doubleValue).sum();
	 * if (Double.compare(totalReparti, rec.getMontant()) != 0) { throw new
	 * IllegalArgumentException("La somme répartie ne correspond pas au montant du recouvrement"
	 * ); }
	 * 
	 * for (Map.Entry<Long, Double> entry : repartitionMontants.entrySet()) { Long
	 * risqueId = entry.getKey(); Double montant = entry.getValue();
	 * 
	 * Risque risque = risqueRepository.findById(risqueId) .orElseThrow(() -> new
	 * RuntimeException("Risque non trouvé"));
	 * 
	 * RecouvrementRisque rr = new RecouvrementRisque();
	 * rr.setMontantAffecte(montant); rr.setRecouvrement(rec); rr.setRisque(risque);
	 * rec.getRepartitions().add(rr); // bidirectionnel si souhaité }
	 * 
	 * rec.setEtat(EtatRecouvrement.VALIDE); recouvrementRepository.save(rec); //
	 * sauvegarde cascade pour RecouvrementRisque
	 * 
	 * return rec; }
	 */
    
    
    public Recouvrement validerRecouvrement(Long recouvrementId, Map<Long, Double> repartitionMontants) {
        Recouvrement rec = recouvrementRepository.findById(recouvrementId)
            .orElseThrow(() -> new RuntimeException("Recouvrement non trouvé"));

        if (!rec.getEtat().equals(EtatRecouvrement.EN_ATTENTE)) {
            throw new IllegalStateException("Ce recouvrement a déjà été traité");
        }

        double totalReparti = repartitionMontants.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Double.compare(totalReparti, rec.getMontant()) != 0) {
            throw new IllegalArgumentException("La somme répartie ne correspond pas au montant du recouvrement");
        }

        for (Map.Entry<Long, Double> entry : repartitionMontants.entrySet()) {
            Long risqueId = entry.getKey();
            Double montant = entry.getValue();
           

            Risque risque = risqueRepository.findById(risqueId)
                .orElseThrow(() -> new RuntimeException("Risque non trouvé"));

            double nouveauSolde = risque.getMontantPrincipale() - montant;
            
            if (nouveauSolde < 0) {
                throw new IllegalArgumentException("Le montant affecté dépasse le montant principal du risque (ID: " + risqueId + ")");
            }

            // Mise à jour du montant principal
            risque.setMontantPrincipale(nouveauSolde);

			

            if (nouveauSolde == 0) {
                Stade stadeCloture = entityManager.getReference(Stade.class, 5L);
                risque.setStade(stadeCloture);
            }

            // Création de l'objet RecouvrementRisque
            RecouvrementRisque rr = new RecouvrementRisque();
            rr.setMontantAffecte(montant);
            rr.setRecouvrement(rec);
            rr.setRisque(risque);
            rec.getRepartitions().add(rr);

            // Sauvegarder le risque mis à jour
            risqueRepository.save(risque);
        }

        rec.setEtat(EtatRecouvrement.VALIDE);
        recouvrementRepository.save(rec); // cascade peut sauver aussi les répartitions

        return rec;
    }

    public void refuserRecouvrement(Long recouvrementId) {
        Recouvrement rec = recouvrementRepository.findById(recouvrementId)
            .orElseThrow(() -> new RuntimeException("Recouvrement non trouvé"));

        rec.setEtat(EtatRecouvrement.REFUSE);
        recouvrementRepository.save(rec);
    }
    
    
    // Lister tous les frais 
    public List<Recouvrement> getAll() {
        return recouvrementRepository.findAllWithDossierAndRepartitions();
    }
}
