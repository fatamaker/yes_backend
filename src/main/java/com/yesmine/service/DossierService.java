package com.yesmine.service;

import com.yesmine.model.Dossier;
import com.yesmine.model.Risque;
import com.yesmine.repository.DossierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DossierService {

    private final DossierRepository dossierRepository;

    public DossierService(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    public Dossier createDossier(Dossier dossier) {
        return dossierRepository.save(dossier);
    }

    public List<Dossier> getAllDossiers() {
        return dossierRepository.findAll();
    }
    public Dossier getByNumero(Long numero) {
        Optional<Dossier> dossierOpt = dossierRepository.findById(numero);
        return dossierOpt.orElse(null);
    }
    
    public List<Risque> getRisquesByDossierNumero(Long numero) {
        Optional<Dossier> dossier = dossierRepository.findById(numero);
        return dossier.map(Dossier::getRisques).orElse(null);
    }
}
