package com.yesmine.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yesmine.model.RecouvrementRisque;

public interface RecouvrementRisqueRepository extends JpaRepository<RecouvrementRisque, Long> {
    List<RecouvrementRisque> findByRecouvrementId(Long recouvrementId);
}

