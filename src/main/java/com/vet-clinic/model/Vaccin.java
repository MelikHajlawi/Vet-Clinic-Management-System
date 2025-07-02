package com.example.MiniProjetSoa.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Vaccin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The repository refers to this as 'nom', so we rename it accordingly.
    private String nom;

    // Ensure the date field matches 'dateVaccination' in the repository queries.
    private LocalDate date;

    // No repository method refers to 'remarque', so we keep it as is.
    private String remarque; // Additional details.

    @ManyToOne
    @JoinColumn(name = "id_animal", nullable = false)
    private Animal animal;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateVaccination() {
        return date;
    }

    public void setDateVaccination(LocalDate dateVaccination) {
        this.date = dateVaccination;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
