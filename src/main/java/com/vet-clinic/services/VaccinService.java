package com.example.MiniProjetSoa.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MiniProjetSoa.exceptions.AnimalNotFoundException;
import com.example.MiniProjetSoa.exceptions.DataNotFoundException;
import com.example.MiniProjetSoa.exceptions.VaccinNotFoundException;
import com.example.MiniProjetSoa.model.Animal;
import com.example.MiniProjetSoa.model.Vaccin;
import com.example.MiniProjetSoa.repository.AnimalRepository;
import com.example.MiniProjetSoa.repository.VaccinRepository;

@Service
public class VaccinService {

    @Autowired
    private VaccinRepository vaccinRepository;

    @Autowired
    private AnimalRepository animalRepository;

 // Obtenir tous les vaccins d'un animal
    public List<Vaccin> getVaccinsForAnimal(Long animalId) {
        List<Vaccin> vaccins = vaccinRepository.findByAnimalId(animalId);
        if (vaccins.isEmpty()) {
            throw new AnimalNotFoundException("Aucun vaccin trouvé pour l'animal avec l'ID : " + animalId);
        }
        return vaccins;
    }
    
    
 // Supprimer un vaccin par ID
    public void deleteVaccin(Long vaccinId) {
        if (!vaccinRepository.existsById(vaccinId)) {
            throw new VaccinNotFoundException("Aucun vaccin trouvé avec l'ID : " + vaccinId);
        }
        vaccinRepository.deleteById(vaccinId);
    }
    
   // mettre a jour un vaccin 
 public Vaccin updateVaccin(Long vaccinId, Vaccin updatedVaccin) {
	    return vaccinRepository.findById(vaccinId).map(existingVaccin -> {
	        existingVaccin.setNom(updatedVaccin.getNom());
	        existingVaccin.setDateVaccination(updatedVaccin.getDateVaccination());
	        existingVaccin.setRemarque(updatedVaccin.getRemarque());
	        // Si l'animal est mis à jour, vérifiez qu'il existe dans la base de données
	        if (updatedVaccin.getAnimal() != null) {
	            Animal updatedAnimal = animalRepository.findById(updatedVaccin.getAnimal().getId())
	                .orElseThrow(() -> new AnimalNotFoundException("Animal introuvable avec l'ID : " + updatedVaccin.getAnimal().getId()));
	            existingVaccin.setAnimal(updatedAnimal);
	        }
	        return vaccinRepository.save(existingVaccin);
	    }).orElseThrow(() -> new VaccinNotFoundException("Aucun vaccin trouvé avec l'ID : " + vaccinId));
	}


    // Ajouter un vaccin à un animal
    public Vaccin addVaccinToAnimal(Long animalId, Vaccin vaccin) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new AnimalNotFoundException("Aucun Animal avec l'ID:  " + animalId));
    

        vaccin.setAnimal(animal);
        return vaccinRepository.save(vaccin);
    }

    // Vérifier si un animal est vacciné
    public boolean isAnimalVaccinated(Long animalId) {
        return vaccinRepository.existsByAnimalId(animalId);
    }
    
    
    public List<Vaccin> getVaccinsByDateAndType(LocalDate date, String animalType) {
        // Si la date et le type d'animal sont tous les deux nuls
        if (date == null && animalType == null) {
            throw new IllegalArgumentException("La date et le type d'animal ne peuvent pas être tous les deux nuls.");
        }

        // Si seule la date est fournie
        if (animalType == null) {
            List<Vaccin> vaccins = vaccinRepository.findByDate(date);
            if (vaccins.isEmpty()) {
                throw new DataNotFoundException("Aucun vaccin trouvé pour la date spécifiée : " + date);
            }
            return vaccins;
        }

        // Si seule la date est nulle mais un type d'animal est fourni
        if (date == null) {
            List<Vaccin> vaccins = vaccinRepository.findByAnimalType(animalType);
            if (vaccins.isEmpty()) {
                throw new DataNotFoundException("Aucun vaccin trouvé pour le type d'animal spécifié : " + animalType);
            }
            return vaccins;
        }

        // Si la date et le type d'animal sont fournis
        List<Vaccin> vaccins = vaccinRepository.findByDateAndAnimalType(date, animalType);
        if (vaccins.isEmpty()) {
            throw new DataNotFoundException("Aucun vaccin trouvé pour la date : " + date + " et le type d'animal : " + animalType);
        }
        return vaccins;
    }

}

