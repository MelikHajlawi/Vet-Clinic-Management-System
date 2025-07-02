package com.example.MiniProjetSoa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.MiniProjetSoa.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{
	
	 // Rechercher des animaux par type; il s agit d' une convention de nommage=> ca traduit en sql en se basant sur le nom de la methode
    List<Animal> findByType(String type);

    // Rechercher des animaux par état de santé
    List<Animal> findByEtatDeSante(String etatDeSante);
    List<Animal> findByTypeAndEtatDeSante(String type, String etatDeSante);
    
    @Query("SELECT COUNT(a) FROM Animal a WHERE (:type = '%' OR a.type = :type) AND(:race = '%' OR a.race = :race) AND(:etatDeSante = '%' OR a.etatDeSante = :etatDeSante)")
     long countByCriteria(@Param("type") String type, 
                          @Param("race") String race, 
                          @Param("etatDeSante") String etatDeSante);


}
