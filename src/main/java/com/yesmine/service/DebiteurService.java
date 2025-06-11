package com.yesmine.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yesmine.DTO.DebiteurDto;
import com.yesmine.DTO.PersonneDto;
import com.yesmine.model.Debiteur;
import com.yesmine.model.Personne;
import com.yesmine.repository.DebiteurRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DebiteurService {

    private final DebiteurRepository repository;
   
 
    public DebiteurDto mapToDTO(Debiteur debiteur) {
        DebiteurDto dto = new DebiteurDto();
        dto.setNumContentieux(debiteur.getNumContentieux());
        dto.setDateTransfert(debiteur.getDateTransfert());
        dto.setSoldeRecouvrement(debiteur.getSoldeRecouvrement());
        dto.setRapporteur(debiteur.getRapporteur());

        // Vérification de null pour éviter NullPointerException
        if (debiteur.getDossier() != null) {
            dto.setNumeroDossier(debiteur.getDossier().getNumero());
        } else {
            dto.setNumeroDossier(null); // ou "N/A" si tu préfères une chaîne
        }

        Personne personne = debiteur.getPersonne();
        if (personne != null) {
            PersonneDto personneDto = new PersonneDto();
            personneDto.setId(personne.getId());
            personneDto.setNom(personne.getNom());
            personneDto.setPrenom(personne.getPrenom());
            dto.setPersonne(personneDto);
        } else {
            dto.setPersonne(null); // sécurité supplémentaire, au cas où
        }

        return dto;
    }




    // Enregistrer un débiteur
    public Debiteur enregistrer(Debiteur debiteur) {
        return repository.save(debiteur);
    }

    // Trouver un débiteur par son numéro de contentieux et le retourner sous forme de DTO
    public List<DebiteurDto> trouverParNumContentieux(Long numContentieux) {
        List<Debiteur> debiteurs = repository.findByNumContentieux(numContentieux);
        return debiteurs.stream()
                        .map(this::mapToDTO)  
                        .collect(Collectors.toList());
    }

    // Trouver les débiteurs par rapporteur et les retourner sous forme de DTO
    public List<DebiteurDto> trouverParRapporteur(String rapporteur) {
        List<Debiteur> debiteurs = repository.findByRapporteur(rapporteur);
        return debiteurs.stream()
                        .map(this::mapToDTO)  // Convertir chaque Debiteur en DebiteurDTO
                        .collect(Collectors.toList());
    }

    // Lister tous les débiteurs et les retourner sous forme de DTO
    public List<DebiteurDto> listerTous() {
        List<Debiteur> debiteurs = repository.findAll();
        return debiteurs.stream()
                        .map(this::mapToDTO)  // Convertir chaque Debiteur en DebiteurDTO
                        .collect(Collectors.toList());
    }
    
    
    public List<Debiteur> searchByNom(String nom) {
        if ((nom == null || nom.isEmpty()) ) {
            return repository.findAll(); // ou Collections.emptyList()
        }
        return repository.findByPersonneNomContainingIgnoreCase(nom);
    }
    

    public Optional<Debiteur> getDebiteurByDossierNumero(Long numeroDossier) {
        return repository.findByDossier_Numero(numeroDossier);
    }
    public Debiteur assignReporter(Long debiteurId, String nomRapporteur) {
        Debiteur debiteur = repository.findById(debiteurId)
                .orElseThrow(() -> new RuntimeException("Débiteur non trouvé"));

        debiteur.setRapporteur(nomRapporteur);
        return repository.save(debiteur);
    }
    
    

}
