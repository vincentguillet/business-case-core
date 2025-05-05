package src.com.humanbooster.model;

import java.time.LocalDateTime;

/**
 * Classe Reservation
 * Décrit une réservation d'une idBorne de recharge par un idUtilisateur avec ses attributs et méthodes
 */
public class Reservation {

    private String id;
    private String idUtilisateur;
    private String idBorne;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private StatutReservation statut;

    /**
     * Constructeur de la classe Reservation
     *
     * @param id            Identifiant unique de la réservation
     * @param idUtilisateur Identifiant de l'utilisateur ayant effectué la réservation
     * @param idBorne       Identifiant de la borne de recharge réservée
     * @param dateDebut     Date et heure de début de la réservation
     * @param dateFin       Date et heure de fin de la réservation
     * @param statut        Statut de la réservation
     */
    public Reservation(String id, String idUtilisateur, String idBorne, LocalDateTime dateDebut, LocalDateTime dateFin, StatutReservation statut) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idBorne = idBorne;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
    }

    /**
     * Récupère l'identifiant unique de la réservation
     *
     * @return L'identifiant unique de la réservation
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la réservation
     *
     * @param id L'identifiant unique de la réservation
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Récupère l'idUtilisateur ayant effectué la réservation
     *
     * @return L'idUtilisateur ayant effectué la réservation
     */
    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    /**
     * Définit l'id de l'utilisateur ayant effectué la réservation
     *
     * @param idUtilisateur L'id de l'utilisateur ayant effectué la réservation
     */
    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    /**
     * Récupère l'id de la borne de recharge réservée
     *
     * @return L'id de la borne de recharge réservée
     */
    public String getIdBorne() {
        return idBorne;
    }

    /**
     * Définit l'id de la borne de recharge réservée
     *
     * @param idBorne L'id de la borne de recharge réservée
     */
    public void setIdBorne(String idBorne) {
        this.idBorne = idBorne;
    }

    /**
     * Récupère la date et l'heure de début de la réservation
     *
     * @return La date et l'heure de début de la réservation
     */
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    /**
     * Définit la date et l'heure de début de la réservation
     *
     * @param dateDebut La date et l'heure de début de la réservation
     */
    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Récupère la date et l'heure de fin de la réservation
     *
     * @return La date et l'heure de fin de la réservation
     */
    public LocalDateTime getDateFin() {
        return dateFin;
    }

    /**
     * Définit la date et l'heure de fin de la réservation
     *
     * @param dateFin La date et l'heure de fin de la réservation
     */
    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Récupère le statut de la réservation
     *
     * @return Le statut de la réservation
     */
    public StatutReservation getStatut() {
        return statut;
    }

    /**
     * Définit le statut de la réservation
     *
     * @param statut Le statut de la réservation
     */
    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }
}
