package com.yesmine.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yesmine.DTO.RecouvrementRequest;
import com.yesmine.model.Recouvrement;
import com.yesmine.service.RecouvrementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recouvrements")
@RequiredArgsConstructor
public class RecouvrementController {

    private final RecouvrementService recouvrementService;

   
    
    @PostMapping("/create")
    public ResponseEntity<Recouvrement> create(@RequestBody RecouvrementRequest request) {
        Recouvrement recouvrement = recouvrementService.createRecouvrement(request.getMontant(), request.getDossierId());
        return ResponseEntity.ok(recouvrement);
    }


    @PostMapping("/{id}/valider")
    public ResponseEntity<Recouvrement> validerRecouvrement(
            @PathVariable Long id,
            @RequestBody Map<Long, Double> repartitionMontants) {
        return ResponseEntity.ok(recouvrementService.validerRecouvrement(id, repartitionMontants));
    }

    @PostMapping("/{id}/refuser")
    public ResponseEntity<Void> refuserRecouvrement(@PathVariable Long id) {
        recouvrementService.refuserRecouvrement(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public List<Recouvrement> getAllRecouvrements() {
        return  recouvrementService.getAll();
    }

}
