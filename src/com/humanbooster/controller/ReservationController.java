package src.com.humanbooster.controller;

import src.com.humanbooster.model.Reservation;
import src.com.humanbooster.model.StatutReservation;
import src.com.humanbooster.service.ReservationService;
import src.com.humanbooster.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Classe ReservationController
 * Permet de gérer les réservations de bornes de recharge
 */
public class ReservationController {

    private final ReservationService reservationService;
    private final Scanner scanner;

    public ReservationController(ReservationService reservationService, Scanner scanner) {
        this.reservationService = reservationService;
        this.scanner = scanner;
    }

    /**
     * Permet à un utilisateur de rechercher une borne disponible et de la réserver
     *
     * @param idUtilisateur L'identifiant de l'utilisateur
     */
    public void rechercherEtReserver(Long idUtilisateur) {
        System.out.println("=== Rechercher une borne disponible ===");

        LocalDateTime debut = saisirDateHeure("Date et heure de début (ex: 2025-05-05 14:00) : ");
        LocalDateTime fin = saisirDateHeure("Date et heure de fin   (ex: 2025-05-05 16:00) : ");

        if (fin.isBefore(debut)) {
            System.out.println("Créneau invalide : la date de fin est antérieure à celle de début.");
            return;
        }

        List<Reservation> disponibles = reservationService.chercherBornesDisponibles(debut, fin);

        if (disponibles.isEmpty()) {
            System.out.println("Aucune borne disponible pour ce créneau.");
            return;
        }

        System.out.println("Bornes disponibles :");
        for (int i = 0; i < disponibles.size(); i++) {
            Reservation r = disponibles.get(i);
            System.out.println((i + 1) + ". Borne ID : " + r.getBorneRecharge());
        }

        System.out.print("Sélectionner une borne (numéro) ou 0 pour annuler : ");
        int choix = Integer.parseInt(scanner.nextLine());

        if (choix < 1 || choix > disponibles.size()) {
            System.out.println("Annulation.");
            return;
        }

        Long idBorne = disponibles.get(choix - 1).getBorneRecharge().getId();
        Reservation r = reservationService.creerReservation(idUtilisateur, idBorne, debut, fin);
        System.out.println("Réservation créée avec statut EN_ATTENTE. ID : " + r.getId());
    }

    /**
     * Affiche les réservations de l'utilisateur connecté
     *
     * @param idUtilisateur L'identifiant de l'utilisateur
     */
    public void afficherMesReservations(Long idUtilisateur) {
        List<Reservation> mesReservations = reservationService.getReservationsUtilisateur(idUtilisateur);

        if (mesReservations.isEmpty()) {
            System.out.println("Aucune réservation trouvée.");
            return;
        }

        // Affichage des réservations
        System.out.println("=== Mes réservations ===");
        for (Reservation r : mesReservations) {
            System.out.println("\n------Réservation " + mesReservations.indexOf(r) + 1 + "------");
            System.out.print(r.toString());
            System.out.println("---------------------------");
        }

    }

    /**
     * Affiche le menu d'administration pour gérer les réservations
     */
    public void menuAdministration() {
        System.out.println("=== Administration des réservations ===");
        List<Reservation> toutes = reservationService.getToutesReservations();

        List<Reservation> reservationsEnAttente = toutes.stream()
                .filter(r -> r.getStatut() == StatutReservation.EN_ATTENTE)
                .toList();

        if (reservationsEnAttente.isEmpty()) {
            System.out.println("Aucune réservation en attente.");
            return;
        }

        // Affichage des réservations en attente
        for (Reservation r : reservationsEnAttente) {
            System.out.println("\n------Réservation " + reservationsEnAttente.indexOf(r) + 1 + "------");
            System.out.print(r.toString());
            System.out.println("---------------------------");
        }

        System.out.print("\nSélectionner une réservation à traiter (numéro) ou 0 pour annuler : ");
        int choix = Integer.parseInt(scanner.nextLine());

        if (choix < 1 || choix > reservationsEnAttente.size()) {
            System.out.println("Annulation.");
            return;
        }

        Reservation selection = reservationsEnAttente.get(choix - 1);
        System.out.print("1. Accepter  2. Refuser : ");
        int action = Integer.parseInt(scanner.nextLine());

        // Traitement de la réservation
        boolean resultat = false;
        if (action == 1) {
            resultat = reservationService.accepterReservation(selection.getId());
        } else if (action == 2) {
            resultat = reservationService.refuserReservation(selection.getId());
        }

        if (resultat) {
            System.out.println("Réservation mise à jour.");
        } else {
            System.out.println("Erreur lors de la mise à jour.");
        }
    }

    /**
     * Saisit une date et une heure au format spécifié
     *
     * @param label Le label à afficher pour la saisie
     * @return La date et l'heure saisies
     */
    private LocalDateTime saisirDateHeure(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine();
            try {
                return LocalDateTime.parse(input, TimeUtil.FORMATTER);
            } catch (Exception e) {
                System.out.println("Format invalide. Attendu : dd/MM/yyyy HH:mm");
            }
        }
    }
}
