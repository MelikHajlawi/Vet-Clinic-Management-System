package com.example.MiniProjetSoa.services;


import com.example.MiniProjetSoa.exceptions.AnimalNotFoundException;
import com.example.MiniProjetSoa.exceptions.VaccinNotFoundException;
import com.example.MiniProjetSoa.model.Animal;
import com.example.MiniProjetSoa.model.Examen;
import com.example.MiniProjetSoa.model.Vaccin;
import com.example.MiniProjetSoa.repository.AnimalRepository;
import com.example.MiniProjetSoa.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExamenService {

    @Autowired
    private ExamenRepository examenRepository;
    
    @Autowired
    private AnimalRepository animalRepository;

    // CRUD Operations
    public Examen createExamen(Long animalId, Examen examen) {
        // Trouver l'animal par son ID
        Animal animal = animalRepository.findById(animalId)
            .orElseThrow(() -> new AnimalNotFoundException("Aucun Animal avec l'ID:  " + animalId));
        
        // Vérifier si le résultat de l'examen est différent de l'état de santé actuel de l'animal
        if (examen.getResultat() != null && !examen.getResultat().equals(animal.getEtatDeSante())) {
            // Si oui, mettre à jour l'état de santé de l'animal
            animal.setEtatDeSante(examen.getResultat());
            animalRepository.save(animal); // Sauvegarder les changements dans la base de données
        }
        
        // Associer l'animal à l'examen et sauvegarder l'examen
        examen.setAnimal(animal);
        return examenRepository.save(examen);
    }

    
    
    

    public List<Examen> getAllExamens() {
        return examenRepository.findAll();
    }

    public Optional<Examen> getExamenById(Long id) {
        return Optional.ofNullable(examenRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException("Aucun Examen avec l'ID:  " + id)));
    }

    public Examen updateExamen(Long examenId, Long animalId, Examen examenDetails) {
        // Trouver l'examen existant par son ID
        Examen examen = examenRepository.findById(examenId)
            .orElseThrow(() -> new AnimalNotFoundException("Examen non trouvé avec l'ID: " + examenId));
        
        // Trouver l'animal par son ID
        Animal animal = animalRepository.findById(animalId)
            .orElseThrow(() -> new AnimalNotFoundException("Cet Examen ne concerne pas l'animal avec l'ID: " + animalId));

        // Mettre à jour les détails de l'examen
        if (examenDetails.getResultat() != null) {
            examen.setResultat(examenDetails.getResultat());
        }
        if (examenDetails.getDateExamen() != null) {
            examen.setDateExamen(examenDetails.getDateExamen());
        }
        if (examenDetails.getTypeExamen() != null) {
            examen.setTypeExamen(examenDetails.getTypeExamen());
        }
        
        // Vous pouvez mettre à jour d'autres attributs de l'examen ici...

        // Vérifier si le résultat de l'examen est différent de l'état de santé actuel de l'animal
        if (examen.getResultat() != null && !examen.getResultat().equals(animal.getEtatDeSante())) {
            // Si oui, mettre à jour l'état de santé de l'animal
            animal.setEtatDeSante(examen.getResultat());
            animalRepository.save(animal); // Sauvegarder les changements dans la base de données
        }

        // Associer l'animal à l'examen et mettre à jour l'examen
        examen.setAnimal(animal);
        return examenRepository.save(examen);
    }


    public void deleteExamen(Long id) {
        if (!examenRepository.existsById(id)) {
            throw new VaccinNotFoundException("Aucun examen trouvé avec l'ID : " + id);
        }
        examenRepository.deleteById(id);
    }
    
    
 // Méthode de recherche dynamique par type d'animal, résultat, et date
    public List<Examen> searchExamen(String typeAnimal, String resultat, LocalDate dateExamen) {
        // Vérification de l'existence des critères dans la base de données
        boolean typeExists = typeAnimal != null && examenRepository.existsByAnimal_Type(typeAnimal);
        boolean resultExists = resultat != null && examenRepository.existsByResultat(resultat);
        boolean dateExists = dateExamen != null && examenRepository.existsByDateExamen(dateExamen);

        // Si un critère n'existe pas, retourner une liste vide ou gérer autrement
        if ((typeAnimal != null && !typeExists) || 
            (resultat != null && !resultExists) || 
            (dateExamen != null && !dateExists)) {
            return List.of(); // Retourner une liste vide ou une autre réponse
        }

        // Si tous les critères sont valides, effectuer la recherche
        if (typeAnimal != null && resultat != null && dateExamen != null) {
            return examenRepository.findByAnimal_TypeAndResultatAndDateExamen(typeAnimal, resultat, dateExamen);
        } else if (typeAnimal != null && resultat != null) {
            return examenRepository.findByAnimal_TypeAndResultat(typeAnimal, resultat);
        } else if (typeAnimal != null && dateExamen != null) {
            return examenRepository.findByAnimal_TypeAndDateExamen(typeAnimal, dateExamen);
        } else if (resultat != null && dateExamen != null) {
            return examenRepository.findByResultatAndDateExamen(resultat, dateExamen);
        } else if (typeAnimal != null) {
            return examenRepository.findByAnimal_Type(typeAnimal);
        } else if (resultat != null) {
            return examenRepository.findByResultat(resultat);
        } else if (dateExamen != null) {
            return examenRepository.findByDateExamen(dateExamen);
        } else {
            return List.of(); // Si aucun critère n'est fourni, retourner une liste vide
        }
    }

   
}

