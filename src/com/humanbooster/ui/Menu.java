package src.com.humanbooster.ui;

import src.com.humanbooster.controller.AuthController;
import src.com.humanbooster.controller.BorneController;
import src.com.humanbooster.controller.LieuController;
import src.com.humanbooster.controller.ReservationController;
import src.com.humanbooster.model.LieuRecharge;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final AuthController authController;
    private final LieuController lieuController;
    private final BorneController borneController;
    private final ReservationController reservationController;

    public Menu(Scanner scanner,
                AuthController authController,
                LieuController lieuController,
                BorneController borneController,
                ReservationController reservationController) {
        this.scanner = scanner;
        this.authController = authController;
        this.lieuController = lieuController;
        this.borneController = borneController;
        this.reservationController = reservationController;
    }

    public void afficherMenu() {
        boolean quitter = false;

        while (!quitter) {
            System.out.println("\n=== Electricity Business ===");
            System.out.println("1. S'inscrire");
            System.out.println("2. Valider l'inscription");
            System.out.println("3. Se connecter");
            System.out.println("4. Rechercher & réserver une borne");
            System.out.println("5. Gérer mes réservations");
            System.out.println("6. Administration (lieux / bornes)");
            System.out.println("0. Quitter");

            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                // S'inscrire
                case "1" -> authController.inscription();
                // Valider l'inscription
                case "2" -> authController.validerInscription();
                // Se connecter
                case "3" -> authController.connexion();
                // Rechercher et réserver une borne
                case "4" -> {
                    if (!authController.estConnecte()) {
                        System.out.println("Veuillez vous connecter.");
                        break;
                    }
                    reservationController.rechercherEtReserver(authController.getUtilisateurConnecte().getId());
                }
                // Gérer les réservations
                case "5" -> {
                    if (!authController.estConnecte()) {
                        System.out.println("Veuillez vous connecter.");
                        break;
                    }
                    reservationController.afficherMesReservations(authController.getUtilisateurConnecte().getId());
                }
                // Menu d'administration
                case "6" -> menuAdmin();
                // Quitter le programme
                case "0" -> {
                    authController.deconnexion();
                    quitter = true;
                    System.out.println("Au revoir !");
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    /**
     * Affiche le menu d'administration pour gérer les lieux, bornes et réservations
     */
    private void menuAdmin() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Gérer les lieux");
            System.out.println("2. Gérer les bornes");
            System.out.println("3. Traiter les réservations");
            System.out.println("r. Retour");

            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                // Gérer les lieux
                case "1" -> {
                    lieuController.afficherTousLesLieux();
                    System.out.println("a. Ajouter | m. Modifier | s. Supprimer | r. Retour");
                    String action = scanner.nextLine();
                    switch (action) {
                        case "a" -> lieuController.ajouterLieu();
                        case "m" -> lieuController.modifierLieu();
                        case "s" -> lieuController.supprimerLieu();
                        case "r" -> retour = true;
                        default -> System.out.println("Choix invalide.");
                    }
                }
                // Gérer les bornes
                case "2" -> {
                    LieuRecharge lieu = lieuController.selectionnerLieu(); // sélection unique
                    if (lieu == null) return;

                    while (!retour) {
                        borneController.listerBornes(lieu);
                        System.out.println("a. Ajouter | m. Modifier | s. Supprimer | r. Retour");
                        String action = scanner.nextLine();
                        switch (action) {
                            case "a" -> borneController.ajouterBorne(lieu);
                            case "m" -> borneController.modifierBorne(lieu);
                            case "s" -> borneController.supprimerBorne(lieu);
                            case "r" -> retour = true;
                            default -> System.out.println("Choix invalide.");
                        }
                    }
                }

                // Traiter les réservations
                case "3" -> reservationController.menuAdministration();
                // Retour au menu principal
                case "r" -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }
}
