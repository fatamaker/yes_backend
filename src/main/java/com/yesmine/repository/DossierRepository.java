package com.yesmine.repository;

import com.yesmine.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
}
