package com.example.MiniProjetSoa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.MiniProjetSoa.model.Vaccin;

@Repository
public interface VaccinRepository extends JpaRepository<Vaccin, Long> {

    // Rechercher les vaccins par ID d'animal
    List<Vaccin> findByAnimalId(Long animalId);

    // Vérifier si un animal est vacciné
    @Query("SELECT COUNT(v) > 0 FROM Vaccin v WHERE v.animal.id = :animalId")
    boolean existsByAnimalId(@Param("animalId") Long animalId);
    
    @Query("SELECT v FROM Vaccin v WHERE v.animal.type = :animalType")
    List<Vaccin> findByAnimalType(@Param("animalType") String animalType);

    // Trouver les vaccins par date
    List<Vaccin> findByDate(LocalDate date);

    // Trouver les vaccins par date et type d'animal
    @Query("SELECT v FROM Vaccin v WHERE v.date = :date AND v.animal.type = :animalType")
    List<Vaccin> findByDateAndAnimalType(@Param("date") LocalDate date, @Param("animalType") String animalType);

    // Trouver les vaccins par nom
    List<Vaccin> findByNom(String nom);

    // Trouver les vaccins par nom et type d'animal
    @Query("SELECT v FROM Vaccin v WHERE v.nom = :nom AND v.animal.type = :animalType")
    List<Vaccin> findByNomAndAnimalType(@Param("nom") String nom, @Param("animalType") String animalType);
}
