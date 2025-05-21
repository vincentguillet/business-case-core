package src.com.humanbooster.controller;

import src.com.humanbooster.model.BorneRecharge;
import src.com.humanbooster.model.EtatBorne;
import src.com.humanbooster.model.LieuRecharge;
import src.com.humanbooster.service.BorneService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Classe BorneController
 * Contrôleur pour gérer les bornes de recharge.
 */
public class BorneController {

    private final BorneService borneService;
    private final LieuController lieuController;
    private final Scanner scanner;

    /**
     * Constructeur de la classe BorneController.
     *
     * @param borneService   Service de gestion des bornes
     * @param lieuController Contrôleur pour gérer les lieux
     * @param scanner        Scanner pour la saisie utilisateur
     */
    public BorneController(BorneService borneService, LieuController lieuController, Scanner scanner) {
        this.borneService = borneService;
        this.lieuController = lieuController;
        this.scanner = scanner;
    }

    /**
     * Ajouter une borne de recharge.
     */
    private void ajouterBorne() {
        LieuRecharge lieu = lieuController.selectionnerLieu();
        if (lieu == null) return;
        ajouterBorne(lieu.getId());
    }

    /**
     * Ajouter une borne de recharge à un lieu spécifique.
     *
     * @param idLieu ID du lieu
     */
    public void ajouterBorne(Long idLieu) {
        System.out.println("=== Ajouter une borne ===");

        System.out.print("Tarif horaire : ");
        double tarif = Double.parseDouble(scanner.nextLine());

        BorneRecharge borne = new BorneRecharge(
                Long.valueOf(UUID.randomUUID().toString()),
                EtatBorne.DISPONIBLE,
                tarif
        );

        boolean ok = borneService.ajouterBorne(idLieu, borne);
        System.out.println(ok ? "Borne ajoutée avec l'ID : " + borne.getId()
                : "Échec : la borne n'a pas pu être ajoutée.");
    }

    /**
     * Modifier une borne de recharge.
     */
    private void modifierBorne() {
        LieuRecharge lieu = lieuController.selectionnerLieu();
        if (lieu == null) return;
        modifierBorne(lieu.getId());
    }

    /**
     * Modifier une borne de recharge sur un lieu spécifique.
     *
     * @param idLieu ID du lieu
     */
    public void modifierBorne(Long idLieu) {
        BorneRecharge borne = selectionnerBorne(idLieu);
        if (borne == null) return;

        System.out.print("Nouveau tarif horaire (" + borne.getTarifHoraire() + ") : ");
        String tarifStr = scanner.nextLine();
        double nouveauTarif = tarifStr.isBlank() ? borne.getTarifHoraire() : Double.parseDouble(tarifStr);

        BorneRecharge modifiee = new BorneRecharge(borne.getId(), borne.getEtat(), nouveauTarif);
        boolean ok = borneService.modifierBorne(idLieu, modifiee);

        System.out.println(ok ? "Borne modifiée." : "Échec de la modification.");
    }

    /**
     * Supprimer une borne de recharge.
     */
    private void supprimerBorne() {
        LieuRecharge lieu = lieuController.selectionnerLieu();
        if (lieu == null) return;
        supprimerBorne(lieu.getId());
    }

    /**
     * Supprimer une borne de recharge su
     * @param idLieu L'id du lieu
     */
    public void supprimerBorne(Long idLieu) {
        BorneRecharge borne = selectionnerBorne(idLieu);
        if (borne == null) return;

        boolean ok = borneService.supprimerBorne(idLieu, borne.getId());
        System.out.println(ok ? "Borne supprimée." : "Échec de la suppression.");
    }

    /**
     * Lister les bornes de recharge
     */
    private void listerBornes() {
        LieuRecharge lieu = lieuController.selectionnerLieu();
        if (lieu == null) return;
        listerBornes(lieu.getId());
    }

    /**
     * Lister les bornes de recharge d'un lieu spécifique.
     *
     * @param idLieu ID du lieu
     */
    public void listerBornes(Long idLieu) {
        List<BorneRecharge> bornes = borneService.listerBornesParLieu(idLieu);
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
     * Sélectionner une borne de recharge.
     *
     * @param idLieu ID du lieu
     * @return BorneRecharge sélectionnée ou null si aucune sélection
     */
    public BorneRecharge selectionnerBorne(Long idLieu) {
        List<BorneRecharge> bornes = borneService.listerBornesParLieu(idLieu);
        if (bornes.isEmpty()) {
            System.out.println("Aucune borne pour ce lieu.");
            return null;
        }

        System.out.println("=== Bornes du lieu ===");
        for (int i = 0; i < bornes.size(); i++) {
            BorneRecharge b = bornes.get(i);
            System.out.println((i + 1) + ". " + b.getEtat() + " - " + b.getTarifHoraire() + "€/h");
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
