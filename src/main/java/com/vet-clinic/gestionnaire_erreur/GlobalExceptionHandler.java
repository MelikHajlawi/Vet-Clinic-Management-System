package com.example.MiniProjetSoa.gestionnaire_erreur;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.MiniProjetSoa.exceptions.AnimalNotFoundException;
import com.example.MiniProjetSoa.exceptions.DataNotFoundException;
import com.example.MiniProjetSoa.exceptions.VaccinNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	  
	@ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException ex) {
        // Retourner un code de statut 224 pour l'animal introuvable
        return ResponseEntity.status(227).body(ex.getMessage());
    }
	
	@ExceptionHandler(VaccinNotFoundException.class)
    public ResponseEntity<String> handleVaccinNotFoundException(VaccinNotFoundException ex) {
        // Retourner un code de statut 224 pour l'animal introuvable
        return ResponseEntity.status(225).body(ex.getMessage());
    }
	
	
	
	 @ExceptionHandler(AnimalNotFoundException.class)
	    public ResponseEntity<String> handleAnimalNotFoundException(AnimalNotFoundException ex) {
	        // Retourner un code de statut 224 pour l'animal introuvable
	        return ResponseEntity.status(224).body(ex.getMessage());
	    }

	 @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
	        if (ex.getMessage().contains("manquant") || ex.getMessage().contains("invalide")) {
	            // Retourner un code 400 pour état de santé manquant ou invalide
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	        }
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur de requête : " + ex.getMessage());
	    }
	 
	 
    // Vous pouvez également gérer d'autres types d'exceptions ici, par exemple :
    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }*/
}