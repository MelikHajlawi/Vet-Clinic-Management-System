package com.example.MiniProjetSoa.repository;

import com.example.MiniProjetSoa.model.Examen;
import com.example.MiniProjetSoa.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

    // Recherche par type d'animal, résultat et date d'examen
    @Query("SELECT e FROM Examen e WHERE " +
            "(:typeAnimal IS NULL OR e.animal.type = :typeAnimal) AND " +
            "(:resultat IS NULL OR e.resultat = :resultat) AND " +
            "(:dateExamen IS NULL OR e.dateExamen = :dateExamen)")
    List<Examen> findByCriteria(String typeAnimal, String resultat, LocalDate dateExamen);
    
    // Recherche avec tous les critères
    List<Examen> findByAnimal_TypeAndResultatAndDateExamen(String typeAnimal, String resultat, LocalDate dateExamen);

    // Recherche par type d'animal et résultat
    List<Examen> findByAnimal_TypeAndResultat(String typeAnimal, String resultat);

    // Recherche par type d'animal et date
    List<Examen> findByAnimal_TypeAndDateExamen(String typeAnimal, LocalDate dateExamen);

    // Recherche par résultat et date
    List<Examen> findByResultatAndDateExamen(String resultat, LocalDate dateExamen);

    // Recherche par type d'animal
    List<Examen> findByAnimal_Type(String typeAnimal);

    // Recherche par résultat
    List<Examen> findByResultat(String resultat);

    // Recherche par date
    List<Examen> findByDateExamen(LocalDate dateExamen);

    // Vérifier l'existence d'un type d'animal
    boolean existsByAnimal_Type(String typeAnimal);

    // Vérifier l'existence d'un résultat
    boolean existsByResultat(String resultat);

    // Vérifier l'existence d'une date d'examen
    boolean existsByDateExamen(LocalDate dateExamen);
}
