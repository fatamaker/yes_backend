package com.yesmine.controller;

import com.yesmine.model.Frais;
import com.yesmine.model.Operation;
import com.yesmine.service.FraisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frais")
@CrossOrigin(origins = "*") 
public class FraisController {

    private final FraisService fraisService;

    public FraisController(FraisService fraisService) {
        this.fraisService = fraisService;
    }

    
    @PostMapping
    public Frais createFrais(@RequestBody Frais frais) {
        return fraisService.createFrais(frais);
    }

 
    @GetMapping
    public List<Frais> getAllFrais() {
        return fraisService.getAllFrais();
    }

    // PUT /api/frais/{id}/valider
    @PutMapping("/{id}/valider")
    public Operation validerFrais(@PathVariable Long id) {
        return fraisService.validerFrais(id);
    }

    // PUT /api/frais/{id}/rejeter
    @PutMapping("/{id}/rejeter")
    public Frais rejeterFrais(@PathVariable Long id) {
        return fraisService.rejeterFrais(id);
    }
}
