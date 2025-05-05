package src.com.humanbooster.service;

import src.com.humanbooster.model.Reservation;

/**
 * Interface pour le service de gestion des documents.
 * Fournit des méthodes pour générer des documents liés aux réservations.
 */
public interface DocumentService {

    void genererRecuReservation(Reservation reservation);
}
