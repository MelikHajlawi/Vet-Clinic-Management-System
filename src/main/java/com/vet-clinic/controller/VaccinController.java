package com.example.MiniProjetSoa.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MiniProjetSoa.model.Vaccin;
import com.example.MiniProjetSoa.services.VaccinService;

@RestController
@RequestMapping("/vaccins")
public class VaccinController {

    @Autowired
    private VaccinService vaccinService;

    // Get all vaccins for a specific animal (Path Parameter)
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<Vaccin>> getVaccinsForAnimal(@PathVariable Long animalId) {
        List<Vaccin> vaccins = vaccinService.getVaccinsForAnimal(animalId);
        return ResponseEntity.ok(vaccins);
    }

    // Add a new vaccin to an animal (Path Parameter + JSON in Body)
    @PostMapping("/animal/{animalId}")
    public ResponseEntity<Vaccin> addVaccinToAnimal(@PathVariable Long animalId, @RequestBody Vaccin vaccin) {
        Vaccin savedVaccin = vaccinService.addVaccinToAnimal(animalId, vaccin);
        return ResponseEntity.ok(savedVaccin);
    }

    // Update a vaccin by ID (Path Parameter + JSON in Body)
    @PutMapping("/{vaccinId}")
    public ResponseEntity<Vaccin> updateVaccin(@PathVariable Long vaccinId, @RequestBody Vaccin updatedVaccin) {
        Vaccin vaccin = vaccinService.updateVaccin(vaccinId, updatedVaccin);
        return ResponseEntity.ok(vaccin);
    }

    // Delete a vaccin by ID (Path Parameter)
    @DeleteMapping("/{vaccinId}")
    public ResponseEntity<String> deleteVaccin(@PathVariable Long vaccinId) {
        vaccinService.deleteVaccin(vaccinId);
        return ResponseEntity.ok("Vaccin "+vaccinId+" supprimé ");
    }

    // Get vaccins by date and/or animal type (Request Parameters)
    @GetMapping("/filter")
    public ResponseEntity<List<Vaccin>> getVaccinsByDateAndType(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String animalType) {
        LocalDate localDate = date != null ? LocalDate.parse(date) : null;
        List<Vaccin> vaccins = vaccinService.getVaccinsByDateAndType(localDate, animalType);
        return ResponseEntity.ok(vaccins);
    }

    // Check if an animal is vaccinated (Path Parameter)
    @GetMapping("/animal/{animalId}/isVaccinated")
    public ResponseEntity<Boolean> isAnimalVaccinated(@PathVariable Long animalId) {
        boolean isVaccinated = vaccinService.isAnimalVaccinated(animalId);
        return ResponseEntity.ok(isVaccinated);
    }
}
