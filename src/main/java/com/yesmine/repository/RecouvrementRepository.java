package com.yesmine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yesmine.model.Recouvrement;

public interface RecouvrementRepository extends JpaRepository<Recouvrement, Long> {
	@Query("SELECT r FROM Recouvrement r WHERE r.dossier.numero = :id")
	List<Recouvrement> findByDossierId(@Param("id") Long id);
	
	  @Query("SELECT DISTINCT r FROM Recouvrement r LEFT JOIN FETCH r.dossier LEFT JOIN FETCH r.repartitions")
	    List<Recouvrement> findAllWithDossierAndRepartitions();

}
