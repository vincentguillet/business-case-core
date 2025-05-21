package com.humanbooster.model;

import jakarta.persistence.*;
import com.humanbooster.util.TimeUtil;

import java.time.LocalDateTime;

/**
 * Classe Reservation
 * Décrit une réservation d'une idBorne de recharge par un idUtilisateur avec ses attributs et méthodes
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "borne_recharge_id")
    private BorneRecharge borneRecharge;

    public Reservation() {}

    public Reservation(LocalDateTime dateDebut, LocalDateTime dateFin, StatutReservation statut, Utilisateur utilisateur, BorneRecharge borneRecharge) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.borneRecharge = borneRecharge;
    }

    /**
     * Récupère l'identifiant unique de la réservation
     *
     * @return L'identifiant unique de la réservation
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la réservation
     *
     * @param id L'identifiant unique de la réservation
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère l'utilisateur ayant effectué la réservation
     *
     * @return L'utilisateur ayant effectué la réservation
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l'utilisateur ayant effectué la réservation
     *
     * @param utilisateur L'utilisateur ayant effectué la réservation
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Récupère la borne de recharge réservée
     *
     * @return La borne de recharge réservée
     */
    public BorneRecharge getBorneRecharge() {
        return borneRecharge;
    }

    /**
     * Définit la borne de recharge réservée
     *
     * @param borneRecharge La borne de recharge réservée
     */
    public void setIdBorne(BorneRecharge borneRecharge) {
        this.borneRecharge = borneRecharge;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Réservation ID : ").append(id).append("\n");
        sb.append("Utilisateur ID : ").append(utilisateur.getId()).append("\n");
        sb.append("Borne ID : ").append(borneRecharge.getId()).append("\n");
        sb.append("Date de début : ").append(dateDebut.format(TimeUtil.FORMATTER)).append("\n");
        sb.append("Date de fin : ").append(dateFin.format(TimeUtil.FORMATTER)).append("\n");
        sb.append("Statut : ").append(statut).append("\n");

        return sb.toString();
    }
}
