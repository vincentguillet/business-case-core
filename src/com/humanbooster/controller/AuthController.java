package src.com.humanbooster.controller;

import src.com.humanbooster.model.Utilisateur;
import src.com.humanbooster.service.AuthService;

import java.util.Scanner;

/**
 * Classe AuthController
 * Contrôleur pour gérer l'authentification des utilisateurs.
 */
public class AuthController {

    private final AuthService authService;
    private final Scanner scanner;

    /**
     * Constructeur de la classe AuthController.
     *
     * @param authService Service d'authentification
     * @param scanner     Scanner pour la saisie utilisateur
     */
    public AuthController(AuthService authService, Scanner scanner) {
        this.authService = authService;
        this.scanner = scanner;
    }

    /**
     * Inscrire un nouvel utilisateur.
     */
    public void inscription() {
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

    /**
     * Valider l'inscription d'un utilisateur.
     */
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

    /**
     * Connexion d'un utilisateur.
     */
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

    /**
     * Déconnexion de l'utilisateur.
     */
    public void deconnexion() {
        authService.deconnecter();
        System.out.println("Déconnexion...");
    }

    /**
     * Vérifie si l'utilisateur est connecté.
     *
     * @return true si l'utilisateur est connecté, false sinon
     */
    public boolean estConnecte() {
        return authService.estConnecte();
    }

    /**
     * Récupère l'utilisateur connecté.
     *
     * @return l'utilisateur connecté
     */
    public Utilisateur getUtilisateurConnecte() {
        return authService.getUtilisateurConnecte();
    }
}
