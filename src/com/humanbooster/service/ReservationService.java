package src.com.humanbooster.service;

import src.com.humanbooster.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface pour le service de gestion des réservations.
 */
public interface ReservationService {

    List<Reservation> chercherBornesDisponibles(LocalDateTime debut, LocalDateTime fin);
    Reservation creerReservation(String idUtilisateur, String idBorne, LocalDateTime debut, LocalDateTime fin);
    boolean accepterReservation(String idReservation);
    boolean refuserReservation(String idReservation);
    List<Reservation> getReservationsUtilisateur(String idUtilisateur);
    List<Reservation> getToutesReservations(); // Pour l'administration
}
