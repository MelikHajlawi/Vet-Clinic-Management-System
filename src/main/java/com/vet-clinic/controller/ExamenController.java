package com.example.MiniProjetSoa.controller;


import com.example.MiniProjetSoa.model.Examen;
import com.example.MiniProjetSoa.services.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/examens")
public class ExamenController {

    @Autowired
    private ExamenService examenService;

    // Créer un examen
    @PostMapping("/{animalId}")
    public ResponseEntity<Examen> createExamen(@PathVariable Long animalId, @RequestBody Examen examen) {
        Examen createdExamen = examenService.createExamen(animalId, examen);
        return new ResponseEntity<>(createdExamen, HttpStatus.CREATED);
    }

    // Récupérer tous les examens
    @GetMapping
    public ResponseEntity<List<Examen>> getAllExamens() {
        List<Examen> examens = examenService.getAllExamens();
        return new ResponseEntity<>(examens, HttpStatus.OK);
    }

    // Récupérer un examen par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Examen> getExamenById(@PathVariable Long id) {
        Optional<Examen> examen = examenService.getExamenById(id);
        return examen.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un examen existant
    @PutMapping("/{examenId}/{animalId}")
    public ResponseEntity<Examen> updateExamen(@PathVariable Long examenId, @PathVariable Long animalId, @RequestBody Examen examenDetails) {
        Examen updatedExamen = examenService.updateExamen(examenId, animalId, examenDetails);
        return new ResponseEntity<>(updatedExamen, HttpStatus.OK);
    }

    // Supprimer un examen par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        examenService.deleteExamen(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Recherche dynamique d'examen par type d'animal, résultat et date
    @GetMapping("/search")
    public ResponseEntity<List<Examen>> searchExamen(
            @RequestParam(required = false) String typeAnimal,
            @RequestParam(required = false) String resultat,
            @RequestParam(required = false) String dateExamen) {
        
        // Convertir la date en LocalDate si elle est présente
        LocalDate date = (dateExamen != null) ? LocalDate.parse(dateExamen) : null;
        
        List<Examen> result = examenService.searchExamen(typeAnimal, resultat, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

