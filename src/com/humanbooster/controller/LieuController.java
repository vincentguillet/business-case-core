package src.com.humanbooster.controller;

import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.service.LieuService;

import java.util.List;
import java.util.Scanner;


/**
 * Classe LieuController
 * Contrôleur pour gérer les lieux de recharge.
 */
public class LieuController {

    private final LieuService lieuService;
    private final Scanner scanner;

    /**
     * Constructeur de la classe LieuController.
     *
     * @param lieuService Service de gestion des lieux
     * @param scanner     Scanner pour la saisie utilisateur
     */
    public LieuController(LieuService lieuService, Scanner scanner) {
        this.lieuService = lieuService;
        this.scanner = scanner;
    }

    /**
     * Affiche le menu de gestion des lieux.
     */
    public void ajouterLieu() {
        System.out.println("=== Ajouter un lieu ===");
        System.out.print("Nom du lieu : ");
        String nom = scanner.nextLine();
        System.out.print("Adresse du lieu : ");
        String adresse = scanner.nextLine();

        LieuRecharge lieu = lieuService.ajouterLieu(nom, adresse);
        System.out.println("Lieu ajouté avec l'ID : " + lieu.getId());
    }

    /**
     * Modifie un lieu existant.
     */
    public void modifierLieu() {
        System.out.println("=== Modifier un lieu ===");
        System.out.print("ID du lieu à modifier : ");
        String id = scanner.nextLine();

        // Vérification de l'existence du lieu
        LieuRecharge lieu = lieuService.trouverParId(id);
        if (lieu == null) {
            System.out.println("Lieu introuvable.");
            return;
        }

        // Affichage des informations actuelles
        System.out.print("Nouveau nom (" + lieu.getNom() + ") : ");
        String nom = scanner.nextLine();
        System.out.print("Nouvelle adresse (" + lieu.getAdresse() + ") : ");
        String adresse = scanner.nextLine();

        // Modification du lieu
        boolean ok = lieuService.modifierLieu(id, nom.isBlank() ? lieu.getNom() : nom,
                adresse.isBlank() ? lieu.getAdresse() : adresse);
        if (ok) System.out.println("Lieu modifié.");
        else System.out.println("Erreur lors de la modification.");
    }

    /**
     * Supprime un lieu existant.
     */
    public void supprimerLieu() {
        System.out.println("=== Supprimer un lieu ===");

        LieuRecharge lieu = selectionnerLieu();
        if (lieu == null) return;

        boolean ok = lieuService.supprimerLieu(lieu.getId());
        System.out.println(ok ? "Lieu supprimé." : "Échec : impossible de supprimer ce lieu.");
    }

    /**
     * Affiche tous les lieux de recharge.
     */
    public void afficherTousLesLieux() {
        List<LieuRecharge> lieux = lieuService.listerLieux();
        if (lieux.isEmpty()) {
            System.out.println("Aucun lieu enregistré.");
            return;
        }

        // Affichage des lieux disponibles
        System.out.println("=== Lieux disponibles ===");
        for (LieuRecharge l : lieux) {
            System.out.println("- " + l.getNom() + " (" + l.getAdresse() + ")");
        }
    }

    /**
     * Sélectionne un lieu de recharge.
     *
     * @return LieuRecharge sélectionné ou null si aucune sélection
     */
    public LieuRecharge selectionnerLieu() {
        List<LieuRecharge> lieux = lieuService.listerLieux();
        if (lieux.isEmpty()) {
            System.out.println("Aucun lieu disponible.");
            return null;
        }

        // Affichage des lieux disponibles
        System.out.println("=== Lieux disponibles ===");
        for (int i = 0; i < lieux.size(); i++) {
            LieuRecharge l = lieux.get(i);
            System.out.println((i + 1) + ". " + l.getNom() + " (" + l.getAdresse() + ")");
        }

        // Sélection d'un lieu
        System.out.print("Sélectionner un lieu (numéro) ou 0 pour annuler : ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix == 0 || choix > lieux.size()) return null;
            return lieux.get(choix - 1);
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide.");
            return null;
        }
    }
}
