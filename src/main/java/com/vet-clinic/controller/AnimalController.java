package com.example.MiniProjetSoa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.MiniProjetSoa.model.Animal;
import com.example.MiniProjetSoa.services.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    // Ajouter un animal
    @PostMapping
    public ResponseEntity<Animal> addAnimal(@RequestBody Animal animal) {
        Animal createdAnimal = animalService.saveAnimal(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimal);
    }

    // Modifier un animal
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal animal) {
        Animal updatedAnimal = animalService.updateAnimal(id, animal);
        return ResponseEntity.ok(updatedAnimal);
    }


    // Supprimer un animal
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable Long id) {
   
            animalService.deleteAnimal(id);
            return ResponseEntity.ok("Animal supprimé avec succès");
        
    }

    // Rechercher un animal par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnimalById(@PathVariable Long id) {
        
            Animal animal = animalService.getAnimalById(id);
            return ResponseEntity.ok(animal);
        
    }

    // Lister tous les animaux
    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    // Rechercher des animaux par type (paramètre de chemin)
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Animal>> getAnimalsByType(@PathVariable String type) {
        List<Animal> animals = animalService.getAnimalsByType(type);
        return ResponseEntity.ok(animals);
    }

    // Rechercher des animaux par état de santé (paramètre de chemin)
    @GetMapping("/etatDeSante/{etat}")
    public ResponseEntity<List<Animal>> getAnimalsByEtatDeSante(@PathVariable String etat) {
        List<Animal> animals = animalService.getAnimalsByEtatDeSante(etat);
        return ResponseEntity.ok(animals);
    }

    // Rechercher des animaux par type et état de santé (paramètres de requête)
    @GetMapping("/search")
    public ResponseEntity<List<Animal>> searchAnimals(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String etatDeSante) {
        List<Animal> animals = animalService.searchAnimals(type, etatDeSante);
        return ResponseEntity.ok(animals);
    }
    
    
    @GetMapping("/count")
    public ResponseEntity<?> countAnimals(@RequestParam(required = false) String type,
                                           @RequestParam(required = false) String race,
                                           @RequestParam(required = false) String etatDeSante) {
        try {
            long count = animalService.countAnimals(type, race, etatDeSante);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur");
        }
    }

    // Exemple d'ajout avec paramètres dans le corps de la requête
    /*@PostMapping("/custom-add")
    public ResponseEntity<String> addAnimalWithCustomParams(@RequestBody Animal animal) {
        if (animal.getType() == null || animal.getEtatDeSante() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Type and EtatDeSante are required");
        }
        Animal createdAnimal = animalService.saveAnimal(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Animal ajouté avec ID: " + createdAnimal.getId());
    }*/
}
