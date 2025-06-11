package com.yesmine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yesmine.model.Risque;
import com.yesmine.model.Stade;

@Repository
public interface RisqueRepository extends JpaRepository<Risque, Long> {
    
    Risque findByNum(String num);
    List<Risque> findByMontantPrincipaleAndStadeNomNot(Double montantPrincipale, String stadeNom);
    
    // Supprime ou remplace celle-ci
    // List<Risque> findByDossierId(Long dossierId);
    
    // Utilise plutôt :
    List<Risque> findByDossierNumero(Long numero);
    
    List<Risque> findByNatureId(Long natureId);
    
    List<Risque> findByStade(Stade stade);
    
    @Query("SELECT r FROM Risque r WHERE r.montantPrincipale = 0 AND r.stade.nom = 'prés-clôture'")
    List<Risque> findRisksReadyForClosure();
    
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
           "FROM Frais f WHERE f.risque = :risque AND f.etat = com.yesmine.model.EtatFrais.EN_ATTENTE")
    Boolean hasUnpaidFees(@Param("risque") Risque risque);
}
