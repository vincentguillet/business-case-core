package src.com.humanbooster.controller;

import src.com.humanbooster.model.Utilisateur;
import src.com.humanbooster.service.AuthService;

import java.util.Scanner;

public class AuthController {

    private final AuthService authService;
    private final Scanner scanner;

    public AuthController(AuthService authService, Scanner scanner) {
        this.authService = authService;
        this.scanner = scanner;
    }

    public void inscrire() {
        System.out.println("=== Inscription ===");
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        Utilisateur u = authService.inscrire(email, mdp);
        if (u != null) {
            System.out.println("Inscription réussie. Un code de validation vous a été envoyé (console).");
        }
    }

    public void validerInscription() {
        System.out.println("=== Validation ===");
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Code reçu : ");
        String code = scanner.nextLine();

        boolean success = authService.validerCompte(email, code);
        if (success) {
            System.out.println("Compte validé !");
        } else {
            System.out.println("Code incorrect ou email inconnu.");
        }
    }

    public void connexion() {
        System.out.println("=== Connexion ===");
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        Utilisateur u = authService.connecter(email, mdp);
        if (u != null) {
            System.out.println("Connexion réussie. Bienvenue, " + u.getEmail());
        } else {
            System.out.println("Échec de la connexion. Vérifiez vos identifiants et la validation du compte.");
        }
    }

    public void deconnexion() {
        authService.deconnecter();
        System.out.println("Déconnecté avec succès.");
    }

    public boolean estConnecte() {
        return authService.estConnecte();
    }

    public Utilisateur getUtilisateurConnecte() {
        return authService.getUtilisateurConnecte();
    }
}
