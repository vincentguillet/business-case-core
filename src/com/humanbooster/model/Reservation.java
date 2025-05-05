package src.com.humanbooster.model;

import java.time.LocalDateTime;

/**
 * Classe Reservation
 * Décrit une réservation d'une borne de recharge par un utilisateur avec ses attributs et méthodes
 */
public class Reservation {

    private int id;
    private Utilisateur utilisateur;
    private BorneRecharge borne;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private StatutReservation statut;

    /**
     * Constructeur de la classe Reservation
     * @param id Identifiant unique de la réservation
     * @param utilisateur Utilisateur ayant effectué la réservation
     * @param borne Borne de recharge réservée
     * @param dateDebut Date et heure de début de la réservation
     * @param dateFin Date et heure de fin de la réservation
     * @param statut Statut de la réservation
     */
    public Reservation(int id, Utilisateur utilisateur, BorneRecharge borne, LocalDateTime dateDebut, LocalDateTime dateFin, StatutReservation statut) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.borne = borne;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
    }

    /**
     * Récupère l'identifiant unique de la réservation
     * @return L'identifiant unique de la réservation
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la réservation
     * @param id L'identifiant unique de la réservation
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Récupère l'utilisateur ayant effectué la réservation
     * @return L'utilisateur ayant effectué la réservation
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l'utilisateur ayant effectué la réservation
     * @param utilisateur L'utilisateur ayant effectué la réservation
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Récupère la borne de recharge réservée
     * @return La borne de recharge réservée
     */
    public BorneRecharge getBorne() {
        return borne;
    }

    /**
     * Définit la borne de recharge réservée
     * @param borne La borne de recharge réservée
     */
    public void setBorne(BorneRecharge borne) {
        this.borne = borne;
    }

    /**
     * Récupère la date et l'heure de début de la réservation
     * @return La date et l'heure de début de la réservation
     */
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    /**
     * Définit la date et l'heure de début de la réservation
     * @param dateDebut La date et l'heure de début de la réservation
     */
    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Récupère la date et l'heure de fin de la réservation
     * @return La date et l'heure de fin de la réservation
     */
    public LocalDateTime getDateFin() {
        return dateFin;
    }

    /**
     * Définit la date et l'heure de fin de la réservation
     * @param dateFin La date et l'heure de fin de la réservation
     */
    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Récupère le statut de la réservation
     * @return Le statut de la réservation
     */
    public StatutReservation getStatut() {
        return statut;
    }

    /**
     * Définit le statut de la réservation
     * @param statut Le statut de la réservation
     */
    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }
}
