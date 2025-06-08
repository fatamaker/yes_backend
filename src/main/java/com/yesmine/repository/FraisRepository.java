package com.yesmine.repository;

import com.yesmine.model.Frais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraisRepository extends JpaRepository<Frais, Long> {
}
