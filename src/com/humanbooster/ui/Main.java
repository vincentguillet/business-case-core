package src.com.humanbooster.ui;

import java.util.Scanner;

public class Main {

    public static void main (String[] args) {


        Scanner scanner = new Scanner(System.in);
        int choixMenu;

        do {
            // Affichage du menu principal
            System.out.println("=== Electricity Business ===");
            System.out.println("1. S'inscrire");
            System.out.println("2. Valider l'inscription");
            System.out.println("3. Se connecter");
            System.out.println("4. Rechercher & réserver une borne");
            System.out.println("5. Gérer mes réservations");
            System.out.println("6. Administration (lieux / bornes)");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            choixMenu = scanner.nextInt();

            switch (choixMenu) {
                case 1:
                    // Inscription
                    break;
                case 2:
                    // Validation d'inscription
                    break;
                case 3:
                    // Connexion
                    break;
                case 4:
                    // Rechercher & réserver une borne
                    break;
                case 5:
                    // Gérer mes réservations
                    break;
                case 6:
                    // Administration (lieux / bornes)
                    break;
                case 0:
                    System.out.println("Merci pour votre visite, à bientôt !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choixMenu != 0);

        // Fermeture du scanner
        scanner.close();
    }
}
