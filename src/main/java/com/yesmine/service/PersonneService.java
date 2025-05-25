package com.yesmine.service;

import com.yesmine.model.Personne;
import com.yesmine.repository.PersonneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }

    public Personne getPersonneById(Long id) {
        return personneRepository.findById(id).orElse(null);
    }

    public Personne savePersonne(Personne personne) {
        return personneRepository.save(personne);
    }

    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }
}
