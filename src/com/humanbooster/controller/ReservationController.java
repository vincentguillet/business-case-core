package src.com.humanbooster.controller;

import src.com.humanbooster.model.Reservation;
import src.com.humanbooster.model.StatutReservation;
import src.com.humanbooster.service.ReservationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ReservationController {


    private final ReservationService reservationService;
    private final Scanner scanner;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ReservationController(ReservationService reservationService, Scanner scanner) {
        this.reservationService = reservationService;
        this.scanner = scanner;
    }

    public void rechercherEtReserver(String idUtilisateur) {
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
            System.out.println((i + 1) + ". Borne ID : " + r.getIdBorne());
        }

        System.out.print("Sélectionner une borne (numéro) ou 0 pour annuler : ");
        int choix = Integer.parseInt(scanner.nextLine());

        if (choix < 1 || choix > disponibles.size()) {
            System.out.println("Annulation.");
            return;
        }

        String idBorne = disponibles.get(choix - 1).getIdBorne();
        Reservation r = reservationService.creerReservation(idUtilisateur, idBorne, debut, fin);
        System.out.println("Réservation créée avec statut EN_ATTENTE. ID : " + r.getId());
    }

    public void afficherMesReservations(String idUtilisateur) {
        List<Reservation> mesReservations = reservationService.getReservationsUtilisateur(idUtilisateur);

        if (mesReservations.isEmpty()) {
            System.out.println("Aucune réservation trouvée.");
            return;
        }

        System.out.println("=== Mes réservations ===");
        for (Reservation r : mesReservations) {
            System.out.println("- [" + r.getId() + "] Borne: " + r.getIdBorne()
                    + " | Du: " + r.getDateDebut()
                    + " au " + r.getDateFin()
                    + " | Statut: " + r.getStatut());
        }
    }

    public void menuAdministration() {
        System.out.println("=== Administration des réservations ===");
        List<Reservation> toutes = reservationService.getToutesReservations();

        List<Reservation> enAttente = toutes.stream()
                .filter(r -> r.getStatut() == StatutReservation.EN_ATTENTE)
                .toList();

        if (enAttente.isEmpty()) {
            System.out.println("Aucune réservation en attente.");
            return;
        }

        for (int i = 0; i < enAttente.size(); i++) {
            Reservation r = enAttente.get(i);
            System.out.println((i + 1) + ". [" + r.getId() + "] Borne: " + r.getIdBorne()
                    + " | Utilisateur: " + r.getIdUtilisateur()
                    + " | Du: " + r.getDateDebut()
                    + " au " + r.getDateFin());
        }

        System.out.print("Sélectionner une réservation à traiter (numéro) ou 0 pour annuler : ");
        int choix = Integer.parseInt(scanner.nextLine());

        if (choix < 1 || choix > enAttente.size()) {
            System.out.println("Annulation.");
            return;
        }

        Reservation selection = enAttente.get(choix - 1);
        System.out.print("1. Accepter  2. Refuser : ");
        int action = Integer.parseInt(scanner.nextLine());

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

    private LocalDateTime saisirDateHeure(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine();
            try {
                return LocalDateTime.parse(input, FORMATTER);
            } catch (Exception e) {
                System.out.println("Format invalide. Attendu : yyyy-MM-dd HH:mm");
            }
        }
    }
}
