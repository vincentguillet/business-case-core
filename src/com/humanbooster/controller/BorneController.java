package src.com.humanbooster.controller;

import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.EtatBorne;
import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.service.BorneService;

import java.util.List;
import java.util.Scanner;

/**
 * Contrôleur pour gérer les bornes de recharge.
 */
public class BorneController {

    private final BorneService borneService;
    private final LieuController lieuController;
    private final Scanner scanner;

    /**
     * Constructeur de la classe BorneController.
     *
     * @param borneService   Service de gestion des bornes.
     * @param lieuController Contrôleur de gestion des lieux.
     * @param scanner        Scanner pour la saisie utilisateur.
     */
    public BorneController(BorneService borneService, LieuController lieuController, Scanner scanner) {
        this.borneService = borneService;
        this.lieuController = lieuController;
        this.scanner = scanner;
    }

    /**
     * Affiche le menu de gestion des bornes.
     */
    private void ajouterBorne() {
        LieuRecharge lieu = lieuController.selectionnerLieu();
        if (lieu == null) return;
        ajouterBorne(lieu);
    }

    /**
     * Ajoute une borne de recharge à un lieu donné.
     *
     * @param lieu Lieu où ajouter la borne.
     */
    public void ajouterBorne(LieuRecharge lieu) {
        System.out.println("=== Ajouter une borne ===");

        System.out.print("Tarif horaire : ");
        double tarif = Double.parseDouble(scanner.nextLine());

        BorneRecharge borne = new BorneRecharge(EtatBorne.DISPONIBLE, tarif, lieu);

        boolean ok = borneService.ajouterBorne(lieu.getId(), borne);
        System.out.println(ok ? "Borne ajoutée avec succès." : "Échec : la borne n'a pas pu être ajoutée.");
    }

    /**
     * Modifie une borne de recharge d'un lieu donné.
     *
     */
    private void modifierBorne() {
        LieuRecharge lieu = lieuController.selectionnerLieu();
        if (lieu == null) return;
        modifierBorne(lieu);
    }

    /**
     * Modifie une borne de recharge d'un lieu donné.
     *
     * @param lieu Lieu où se trouve la borne à modifier.
     */
    public void modifierBorne(LieuRecharge lieu) {
        BorneRecharge borne = selectionnerBorne(lieu);
        if (borne == null) return;

        System.out.print("Nouveau tarif horaire (" + borne.getTarifHoraire() + ") : ");
        String tarifStr = scanner.nextLine();
        double nouveauTarif = tarifStr.isBlank() ? borne.getTarifHoraire() : Double.parseDouble(tarifStr);

        borne.setTarifHoraire(nouveauTarif);

        boolean ok = borneService.modifierBorne(lieu.getId(), borne);
        System.out.println(ok ? "Borne modifiée." : "Échec de la modification.");
    }

    /**
     * Supprime une borne de recharge d'un lieu donné.
     *
     */
    private void supprimerBorne() {
        LieuRecharge lieu = lieuController.selectionnerLieu();
        if (lieu == null) return;
        supprimerBorne(lieu);
    }

    /**
     * Supprime une borne de recharge d'un lieu donné.
     *
     * @param lieu Lieu où se trouve la borne à supprimer.
     */
    public void supprimerBorne(LieuRecharge lieu) {
        BorneRecharge borne = selectionnerBorne(lieu);
        if (borne == null) return;

        boolean ok = borneService.supprimerBorne(lieu.getId(), borne.getId());
        System.out.println(ok ? "Borne supprimée." : "Échec de la suppression.");
    }

    /**
     * Liste les bornes de recharge d'un lieu donné.
     *
     */
    private void listerBornes() {
        LieuRecharge lieu = lieuController.selectionnerLieu();
        if (lieu == null) return;
        listerBornes(lieu);
    }

    /**
     * Liste les bornes de recharge d'un lieu donné.
     *
     * @param lieu Lieu dont on veut lister les bornes.
     */
    public void listerBornes(LieuRecharge lieu) {
        List<BorneRecharge> bornes = borneService.listerBornesParLieu(lieu.getId());
        if (bornes.isEmpty()) {
            System.out.println("Aucune borne pour ce lieu.");
            return;
        }

        System.out.println("=== Bornes du lieu ===");
        for (int i = 0; i < bornes.size(); i++) {
            BorneRecharge b = bornes.get(i);
            System.out.println((i + 1) + ". " + b.getEtat() + " - " + b.getTarifHoraire() + " €/h");
        }
    }

    /**
     * Sélectionne une borne de recharge d'un lieu donné.
     *
     * @param lieu Lieu dont on veut sélectionner une borne.
     * @return La borne sélectionnée ou null si aucune sélectionnée.
     */
    public BorneRecharge selectionnerBorne(LieuRecharge lieu) {
        List<BorneRecharge> bornes = borneService.listerBornesParLieu(lieu.getId());
        if (bornes.isEmpty()) {
            System.out.println("Aucune borne pour ce lieu.");
            return null;
        }

        System.out.println("=== Bornes du lieu ===");
        for (int i = 0; i < bornes.size(); i++) {
            BorneRecharge b = bornes.get(i);
            System.out.println((i + 1) + ". " + b.getEtat() + " - " + b.getTarifHoraire() + " €/h");
        }

        System.out.print("Sélectionner une borne (numéro) ou 0 pour annuler : ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix == 0 || choix > bornes.size()) return null;
            return bornes.get(choix - 1);
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide.");
            return null;
        }
    }
}
