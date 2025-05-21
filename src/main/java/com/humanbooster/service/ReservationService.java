package com.humanbooster.service;

import com.humanbooster.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface pour le service de gestion des réservations.
 */
public interface ReservationService {

    List<Reservation> chercherBornesDisponibles(LocalDateTime debut, LocalDateTime fin);
    Reservation creerReservation(Long idUtilisateur, Long idBorne, LocalDateTime debut, LocalDateTime fin);
    boolean accepterReservation(Long idReservation);
    boolean refuserReservation(Long idReservation);
    List<Reservation> getReservationsUtilisateur(Long idUtilisateur);
    List<Reservation> getToutesReservations(); // Pour l'administration
}
