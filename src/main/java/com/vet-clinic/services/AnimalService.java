package com.example.MiniProjetSoa.services;

import org.springframework.stereotype.Service;

import com.example.MiniProjetSoa.exceptions.AnimalNotFoundException;
import com.example.MiniProjetSoa.model.Animal;
import com.example.MiniProjetSoa.repository.AnimalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired // pour injecter automatiquement une instance de AnimalService dans cette classe
    private AnimalRepository animalRepository;

    // Ajouter un animal
    public Animal saveAnimal(Animal animal) {
        // Vérification que tous les champs nécessaires sont présents et valides
        if (animal.getNom() == null || animal.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'animal est requis.");
        }
        if (animal.getType() == null || animal.getType().isEmpty()) {
            throw new IllegalArgumentException("Le type de l'animal est requis.");
        }
        if (animal.getEtatDeSante() == null || animal.getEtatDeSante().isEmpty()) {
            throw new IllegalArgumentException("L'état de santé de l'animal est requis.");
        }
        if (animal.getPoids() <= 0) {
            throw new IllegalArgumentException("Le poids de l'animal doit être supérieur à zéro.");
        }
        if (animal.getRace() == null || animal.getRace().isEmpty()) {
            throw new IllegalArgumentException("La race de l'animal est requise.");
        }

        // Si toutes les validations passent, enregistrez l'animal
        return animalRepository.save(animal);
    }


    // Modifier un animal, normalement .save de l interface AnimalRepository fait l affaire parceque si l animale se trouve, il sera mis a jour
    public Animal updateAnimal(Long id, Animal updatedAnimal) {
        Animal existingAnimal = animalRepository.findById(id)
            .orElseThrow(() -> new AnimalNotFoundException("Animal introuvable avec ID: " + id));
        // Mettre à jour les champs
        existingAnimal.setNom(updatedAnimal.getNom());
        existingAnimal.setType(updatedAnimal.getType());
        existingAnimal.setEtatDeSante(updatedAnimal.getEtatDeSante());
        existingAnimal.setPoids(updatedAnimal.getPoids());
        existingAnimal.setRace(updatedAnimal.getRace());
        return animalRepository.save(existingAnimal);
    }


    // Supprimer un animal
    public void deleteAnimal(Long id) {
        Optional<Animal> animalOptional = animalRepository.findById(id);
        if (animalOptional.isPresent()) {
            animalRepository.deleteById(id);
        } else {
            throw new AnimalNotFoundException("Aucun Animal avec l'ID:  " + id);
        }
    }

    // Rechercher un animal par ID
    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException("Aucun Animal avec l'ID:  " + id));
    }

    // Lister tous les animaux
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    // Rechercher des animaux par type
    public List<Animal> getAnimalsByType(String type) {
    	
        	 List<Animal> animals = animalRepository.findByType(type);
             if (animals.isEmpty()) {
                 throw  new AnimalNotFoundException("Aucun Animal avec le Type:  " + type);
             }
             return animals;
        	
        	
        }
       
    

    

    // Rechercher des animaux par état de santé
    public List<Animal> getAnimalsByEtatDeSante(String etatDeSante) {
        if (etatDeSante == null || etatDeSante.trim().isEmpty()) {
            throw new IllegalArgumentException("L'état de santé est manquant ou invalide.");
        }

        List<Animal> animals = animalRepository.findByEtatDeSante(etatDeSante);

        if (animals.isEmpty()) {
            throw new AnimalNotFoundException("Aucun animal trouvé avec l'état de santé : " + etatDeSante);
        }

        return animals;
    }

    
    
    
    
    public List<Animal> searchAnimals(String type, String etatDeSante) {
        List<Animal> animals;

        if (type != null && etatDeSante != null) {
            // Rechercher par type et état de santé
            animals = animalRepository.findByTypeAndEtatDeSante(type, etatDeSante);
        } else if (type != null) {
            // Rechercher uniquement par type
            animals = animalRepository.findByType(type);
        } else if (etatDeSante != null) {
            // Rechercher uniquement par état de santé
            animals = animalRepository.findByEtatDeSante(etatDeSante);
        } else {
            // Retourner tous les animaux si aucun critère n'est fourni
            animals = animalRepository.findAll();
        }

        // Vérifier si la liste est vide et lancer une exception personnalisée
        if (animals.isEmpty()) {
            throw new AnimalNotFoundException("Aucun animal trouvé avec les critères spécifiés.");
        }

        return animals;
    }
    
    
    
    public long countAnimals(String type, String race, String etatDeSante) {
        // Vérifiez si tous les critères sont absents et retournez un total global
        if (type == null && race == null && etatDeSante == null) {
            return animalRepository.count(); // Total général
        }

        // Gestion conditionnelle selon les critères fournis
        return animalRepository.countByCriteria(
            Optional.ofNullable(type).orElse("%"),
            Optional.ofNullable(race).orElse("%"),
            Optional.ofNullable(etatDeSante).orElse("%")
        );
    }
    
    
    

}